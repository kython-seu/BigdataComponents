import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkDemo {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("SparkDEMO")
        .setMaster("yarn")
      //.setMaster("spark://kason-pc:7077")
      .set("spark.yarn.jars","hdfs://kason-pc:9000/system/spark/yarn/jars/*")
        .setJars(List("/home/kason/workspace/BigdataComponents/out/artifacts/SparkLearn_jar/SparkLearn.jar"))
      //.setJars(GETJars.getJars("/home/kason/workspace/BigdataComponents/spark-main/target/spark-main/WEB-INF/lib"))
    val sc: SparkContext = new SparkContext(conf)


    val dataRDD: RDD[Int] = sc.parallelize(Array(1,2,3,4))

    val result = dataRDD.map(res => res * 2)
    result.collect().foreach(println(_))
    print(result.count())
  }
}
