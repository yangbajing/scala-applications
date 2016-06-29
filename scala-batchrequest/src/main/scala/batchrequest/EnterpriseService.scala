package batchrequest

import akka.actor.ActorSystem
import akka.util.Timeout
import scala.concurrent.duration._

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-06-29.
  */
class EnterpriseService(actorSystem: ActorSystem,
                        infraResource: InfraResource,
                        infraMongodbRepo: InfraMongodbRepo) {

  private val master = actorSystem.actorOf(CompanyMaster.props(infraResource, infraMongodbRepo), "infra-company")
  private implicit val timeout = Timeout(60.seconds)

}
