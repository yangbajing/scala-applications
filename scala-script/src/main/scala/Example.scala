import scala.annotation.tailrec

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-05-30.
  */
object Example {

  @tailrec
  def test(n: Int, acc: Int): Int = {
    if (n <= 0) acc
    else test(n - 1, n * acc)
  }

  def main(args: Array[String]): Unit = {
    test(7, 1)
    val begin = System.nanoTime()
    val result = test(200, 1)
    val end = System.nanoTime()
    println(s"result: $result, cost time: ${end - begin}ns")
  }

}
