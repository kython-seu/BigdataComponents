package sparkRDD

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkRDDApp {
  def createCombine(data: (String, Double)):(Int, Double) = {

    (1, data._2)

  }
  def combineInPartition(data: (Int, Double), valueRaw: (String, Double)) : (Int, Double)= {
    (data._1 + 1, data._2 + valueRaw._2)
  }

  def combineBetweenPartition(dataP1: (Int, Double), dataP2: (Int, Double)): (Int, Double) = {
    (dataP1._1 + dataP2._1, dataP1._2 + dataP2._2)
  }
  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("SparkRdd test")
    val sc: SparkContext = new SparkContext(conf)
    

    val text: RDD[Array[String]] = sc.textFile("/home/kason/data/sparkrdd/data").map(str => str.split(" ")).persist()
    val data1: RDD[(String,Int)] = text.map(res => (res(0), res(1).toInt))
    val data2: RDD[(String, Long)] = text.map(res => (res(0), res(2).toLong))

    val result: RDD[(String, (Iterable[Int], Iterable[Long]))] = data1.cogroup(data2)
    result.foreach(println)
    text.unpersist()

    //combineByKey

    val initialScores = Array(("Fred", ("语文",88.0)), ("Fred", ("英语",95.0)), ("Fred", ("地理",91.0)), ("Wilma", ("语文",93.0)), ("Wilma", ("英语",95.0)), ("Wilma", ("地理",98.0)))
    val d1 = sc.parallelize(initialScores)
    type MVType = (Int, Double) //定义一个元组类型(科目计数器,分数)
    val result2: RDD[(String, (Int, Double))] = d1.combineByKey(createCombine,combineInPartition, combineBetweenPartition)

    result2.map({case (name, (num, store)) => (name, store/num)}).foreach(println)
  }

}
