import scala.annotation.tailrec

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-05-30.
  */
object ScalaExample {

  @tailrec
  def factorial(n: Int, acc: Long): Long = {
    if (n <= 0) acc
    else factorial(n - 1, n * acc)
  }

  def main(args: Array[String]): Unit = {
    factorial(20, 1L)
    var i = 0
    var result = 0L
    val begin = System.nanoTime()
    while (i < 10) {
      result = factorial(20, 1L)
      i += 1
    }
    val end = System.nanoTime()
    println(s"result: $result, cost time: ${end - begin} ns")
  }

}
