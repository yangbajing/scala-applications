package batchrequest

import akka.actor.ActorSystem

import scala.concurrent.Await
import scala.util.{Failure, Success}
import scala.concurrent.duration._

/**
  * 合并同一时间的3个请求
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-06-29.
  */
object BatchExample {
  val system = ActorSystem()

  import system.dispatcher

  def main(args: Array[String]): Unit = {
    val infraResource = new InfraResource()
    val infraMongodbRepo = new InfraMongodbRepo()
    val enterpriseService = new EnterpriseService(system, infraResource, infraMongodbRepo)

    val company1Future = enterpriseService.getCorpDetail("科技公司")
    val company2Future = enterpriseService.getCorpDetail("科技公司")
    val company3Future = enterpriseService.getCorpDetail("科技公司")

    val companiesFuture = for {
      company1 <- company1Future
      company2 <- company2Future
      company3 <- company3Future
    } yield {
      List(company1, company2, company3)
    }

    val future = Await.ready(companiesFuture, 60.seconds)
    future.onComplete {
      case Success(c1 :: c2 :: c3 :: Nil) =>
        println(c1 eq c2)
        println(c2 eq c3)
        println(s"$c1, $c2, $c3")
      case Success(unknown) =>
        System.err.println(s"收到未期待的结果：$unknown")

      case Failure(e) =>
        e.printStackTrace()
    }

    try {
      Await.result(system.terminate(), 60.seconds)
    } catch {
      case e: Throwable =>
        e.printStackTrace()
        System.exit(3)
    }
  }

}
