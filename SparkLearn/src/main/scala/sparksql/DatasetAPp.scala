package sparksql

import org.apache.spark.sql.{Dataset, SparkSession}

object DatasetAPp {
  case class Person(name: String, age: Int)

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .appName("SParkSession")
      .master("local[2]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    import spark.implicits._

    //(1) CREATE DATASET by method createDataset

    val dataStr: Seq[String] = Seq("a","b")

    val datasetStr: Dataset[String] = spark.createDataset[String](dataStr)

    datasetStr.show()

    val dataPerson: Seq[Person] = Seq(Person("lily",18),Person("mike",25),Person("hanmeimei",30),Person("lily",25))

    val datasetPerson: Dataset[Person] = spark.createDataset(dataPerson)

    datasetPerson.show()

    datasetPerson.groupBy("name").count().show()

    println("=============mehod2===========")
    //(2) SEQ 转DataFrame, DataFRame转Dataset
    val dataStrFrame = datasetStr.toDF()
    val dataStrFrameToSet = dataStrFrame.as[String]
    dataStrFrameToSet.show()
    val dataPersonSeq = Seq(("lily",18),("lucy",20))
    val dataPersonFrame2Set_1: Dataset[Person] = dataPersonSeq.map(res => Person(res._1, res._2)).toDS()
    val dataPersonFrame2Set_2: Dataset[Person] = dataPersonSeq.map(res => Person(res._1, res._2)).toDF().as[Person]
    dataPersonFrame2Set_1.show()
    dataPersonFrame2Set_2.show()
    //(3) 直接转为dataset
    println("==============method 3===========")
    val dataSeq2Set: Dataset[Person] = dataPerson.toDS()
    dataSeq2Set.show()
  }
}
