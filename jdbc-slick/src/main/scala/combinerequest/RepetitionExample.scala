package combinerequest

import akka.actor.ActorSystem
import combinerequest.domain.Company
import combinerequest.service.{CompanyService, InfraMongodbRepo, InfraResource}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-07-02.
  */
object RepetitionExample {
  val system = ActorSystem()

  import system.dispatcher

  def main(args: Array[String]): Unit = {
    val infraResource = new InfraResource()
    val infraMongodbRepo = new InfraMongodbRepo()
    val enterpriseService = new RepetitionService(infraResource, infraMongodbRepo)

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

    Await.ready(companiesFuture, 60.seconds)
      .onComplete {
        case Success(c1 :: c2 :: c3 :: Nil) =>
//          println(c1 eq c2)
//          println(c2 eq c3)
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

class RepetitionService(val infraResource: InfraResource,
                        val infraMongodbRepo: InfraMongodbRepo) extends CompanyService {

  override def getCorpDetail(companyName: String)(implicit ec: ExecutionContext): Future[Option[Company]] = {
    infraMongodbRepo.findCorpDetail(companyName)
      .flatMap {
        case Some(company) =>
          Future.successful(Some(company))
        case None =>
          infraResource.corpDetail(companyName)
            .flatMap {
              case Some(company) =>
                infraMongodbRepo
                  .saveCorpDetail(companyName, company)
                  .map(_ => Some(company))

              case None =>
                Future.successful(None)
            }
      }
  }

}
