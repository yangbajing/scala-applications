package combinerequest.service

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import combinerequest.domain.Company
import combinerequest.message.{GetCompanyMessage, GetCorpDetail}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-06-29.
  */
trait CompanyService {
  val infraResource: InfraResource
  val infraMongodbRepo: InfraMongodbRepo

  def getCorpDetail(companyName: String)(implicit ec: ExecutionContext): Future[Option[Company]]
}

class EnterpriseService(actorSystem: ActorSystem,
                        val infraResource: InfraResource,
                        val infraMongodbRepo: InfraMongodbRepo) extends CompanyService {

  private val master = actorSystem.actorOf(CompanyMaster.props(infraResource, infraMongodbRepo), "infra-company")
  private implicit val timeout = Timeout(60.seconds)

  @inline
  private def getResult(message: GetCompanyMessage)(implicit ec: ExecutionContext) =
    master.ask(message).mapTo[Option[Company]]

  def getCorpDetail(companyName: String)(implicit ec: ExecutionContext) = getResult(GetCorpDetail(companyName))

}
