package wiki

//import org.apache.flink.streaming.api.scala._

import org.apache.flink.api.common.functions.FoldFunction
import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{DataStream, KeyedStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010
import org.apache.flink.streaming.connectors.wikiedits.{WikipediaEditEvent, WikipediaEditsSource}
import org.apache.flink.streaming.util.serialization.SimpleStringSchema



object WIkiAnalysis {
  case class WikiModel(user: String,title: String,time: Long, summary: String, diffUrl: String, channel: String, byteDiff: Int)
  def getWikiEvent(data: WikiModel, event: WikipediaEditEvent): WikiModel = {
    val user: String = event.getUser
    val title: String = event.getTitle
    val time: Long = event.getTimestamp
    val summary: String = event.getSummary
    val diffUrl: String = event.getDiffUrl
    val channel: String = event.getChannel
    val byteDiff: Int = event.getByteDiff

    WikiModel(user,title,time,summary,diffUrl,channel,byteDiff)
  }
  def sum(data:(String, String,Long),event: WikipediaEditEvent): (String, String, Long) = {
    val a1 = event.getUser
    val title = event.getTitle
    val a2 = data._3 + event.getByteDiff
    (a1, title, a2)
  }
  def main(args: Array[String]): Unit = {

    // get the execution environment
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val edits:DataStream[WikipediaEditEvent] = env.addSource(new WikipediaEditsSource())

    /**
      * 假设五秒钟，我们监控这段时间内在特定窗口中用户产生的添加或者删除的字节数。
      * 为此，我们首先应该指定此用户名上的我们想要的输入流，意思是在这个流的操作上我们应该绑定用户名。
      * 在我们的示例中，窗口中编辑的字节总数应该是每一个唯一的用户
      */
    //val keyedEdits = edits.keyBy({event:WikipediaEditEvent => event.getUser})
    val keyedEdits:KeyedStream[WikipediaEditEvent, String] = edits.keyBy({ (event: WikipediaEditEvent) => {
      event.getUser
    }
    })

    //keyedEdits.print()
    val result = keyedEdits
      .timeWindow(Time.seconds(5))
      .fold(("","",0L))(sum)

    val secondR = keyedEdits
      .timeWindow(Time.seconds(5))

      .fold(WikiModel("","",0L,"","","",0))(getWikiEvent)
      .map(res => res.user)
      .addSink(new FlinkKafkaProducer010[String]("kason-pc:9092","wiki-result",new SimpleStringSchema()))
      //.keyBy("user")

      //.sum("byteDiff")
    //secondR.print()
    //result.print()

    env.execute()

  }

}
