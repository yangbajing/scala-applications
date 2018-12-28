package combinerequest.service

import akka.actor.Props
import combinerequest.service.ForwardCompanyActor.{ReadFromDB, ReadFromInfra}

import scala.concurrent.Future

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-06-29.
  */
class CorpDetailActor(infraResource: InfraResource,
                      infraMongodbRepo: InfraMongodbRepo) extends ForwardCompanyActor {

  import context.dispatcher

  override val readFromDB: ReadFromDB = (companyName) => {
    infraMongodbRepo.findCorpDetail(companyName)
  }

  override val readFromInfra: ReadFromInfra = (companyName) => {
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

object CorpDetailActor {
  def props(infraResource: InfraResource,
            infraMongodbRepo: InfraMongodbRepo) =
    Props(new CorpDetailActor(infraResource, infraMongodbRepo))
}
