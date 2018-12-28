package first

import org.scalamock.scalatest.MockFactory

import scala.collection.mutable
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

import scala.concurrent.Future

class FirstTest extends WordSpec with MustMatchers with OptionValues with ScalaFutures  with MockFactory {

    override implicit def patienceConfig = PatienceConfig(Span(60, Seconds), Span(50, Millis))

  //  "A Stack" should {
  //    "pop values in last-in-first-out order" in {
  //      val stack = mutable.Stack[Int]()
  //
  //      stack.push(1)
  //      stack.push(2)
  //      stack.pop() mustBe 2
  //      stack.pop() mustBe 1
  //    }
  //
  //    "throw NoSuchElementException if an empty stack is popped" in {
  //      val emptyStack = mutable.Stack[Int]()
  //      assertThrows[NoSuchElementException] {
  //        emptyStack.pop()
  //      }
  //    }
  //
  //  }

  //  "option" should {
  //    "value" in {
  //      val v: Option[Int] = None
  //      v.value mustBe 2
  ////      v.get mustBe 1
  //    }
  //
  //    "other" in {
  //      3 mustBe 3
  //    }
  //  }

  //  "future" should {
  //    "await result === 3" in {
  //      import scala.concurrent.ExecutionContext.Implicits.global
  //      val f = Future{
  //        Thread.sleep(1000)
  //        3
  //      }
  //      val result = f.futureValue
  //      result mustBe 3
  //    }
  //  }

  "scalamock" should {
    "function mock" in {
      val m = mockFunction[Int, String]
      m expects 42 returning "Forty two"
      m(42) mustBe "Forty two"
    }
  }

}