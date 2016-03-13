package example

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-03-12.
  */
class SparkApp(sc: SparkContext) extends Serializable {
  val sqlContext = new SQLContext(sc)

  def run() = {
    val jsonStrings = Seq(
      """{"name":"杨景","age":31}""",
      """{"name":"羊八井","age"31}""",
      """{"name":"yangbajing","age":31}"""
    )
    val rdd = sc.parallelize(jsonStrings)
    val sql = sqlContext.read.json(rdd)
    sql.show()
  }

}

object SparkApp {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    val app = new SparkApp(sc)
    app.run()
  }

}
