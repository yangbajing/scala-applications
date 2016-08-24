package me.yangbajing.demo.common

import scala.util.Random

/**
  * Created by yangbajing on 16-8-24.
  */
object Utils {

  def randomString(length: Int): String = {
    (0 until length).map(_ => Random.nextPrintableChar()).mkString
  }

}
