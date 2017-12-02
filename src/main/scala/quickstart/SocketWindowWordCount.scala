package quickstart
import java.sql.Date
import java.text.SimpleDateFormat

import org.apache.flink.api.scala._
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time

object SocketWindowWordCount {

  def main(args: Array[String]) : Unit = {

    val simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    // the port to connect to
    var port: Int = try {
      ParameterTool.fromArgs(args).getInt("port")
    } catch {
      case e: Exception => {
        System.err.println("No port specified. Please run 'SocketWindowWordCount --port <port>'")
        return
      }
    }

    port=9000
    // get the execution environment
    //val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.createRemoteEnvironment("kason-pc",6123,"/home/kason/workspace/BigdataComponents/target/BigdataComponents-1.0-SNAPSHOT.jar")
    // get input data by connecting to the socket
    val text: DataStream[String] = env.socketTextStream("kason-pc", port, '\n')

    // parse the data, group it, window it, and aggregate the counts
    val windowCounts = text
      .flatMap { w => w.split("\\s") }
      .map { w => WordWithCount(w, 1, simple.format(new Date(System.currentTimeMillis()))) }
      .keyBy("word")
      .timeWindow(Time.seconds(10), Time.seconds(1)) //每隔1s中处理10s内的socket发送的数据
      .sum("count")

    // print the results with a single thread, rather than in parallel
    windowCounts.print().setParallelism(1)

    env.execute("Socket Window WordCount")
  }

  // Data type for words with count
  case class WordWithCount(word: String, count: Long, time: String)
}
