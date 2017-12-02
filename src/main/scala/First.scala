import org.apache.flink.streaming.api.functions.source.FileMonitoringFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
//import org.apache.flink.api.scala.ExecutionEnvironment
//import org.apache.flink.streaming.api.functions.source.FileMonitoringFunction

object First {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val dataFileStream: DataStream[String] = env.readFileStream("/home/kason/data/stream", 10000, FileMonitoringFunction.WatchType.PROCESS_ONLY_APPENDED)
    //env.
    dataFileStream.print()
    env.execute()
  }

}
