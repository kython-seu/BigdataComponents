package com.kason.dataSetAPi

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}

object DataSetApp {


  case class Person(val name: String, val age: Int)

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder().appName("SparkDataSet").master("local[2]").getOrCreate()

    val dataMonitor = Seq(("jack",22),("rose",18),("marrt",17),("lilei",28))
    import spark.implicits._

    val data: Dataset[Person] = dataMonitor.map(data => Person(data._1, data._2)).toDS()

    data.printSchema()

    //展示数据
    data.show()

    //select
    data.select($"name", $"age" + 1).show()

    //filter
    data.filter($"age" > 24).show()

  }

}
