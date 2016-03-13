package example

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.WordSpec

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-03-12.
  */
class SparkAppTest extends WordSpec {

  "SparkAppTest" should {

    "run" in {
      val conf = new SparkConf()
        .setAppName("SparkAppTest")
        .setMaster("local[*]")
      val sc = new SparkContext(conf)

      val app = new SparkApp(sc)
      app.run()
    }

  }
}
