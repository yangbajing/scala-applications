package batchrequest

import akka.actor.{Actor, Props}
import batchrequest.message.{GetCorpDetail, QueryCompany}

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-06-29.
  */
class CompanyMaster(infraResource: InfraResource,
                     infraMongodbRepo: InfraMongodbRepo) extends Actor {

  val actorCorpDetail = context.actorOf(CorpDetailActor.props(infraResource, infraMongodbRepo), "corpDetail")

  override def receive = {
    case GetCorpDetail(companyName) =>
      actorCorpDetail ! QueryCompany(companyName, sender())
  }

}

object CompanyMaster {

  def props(infraResource: InfraResource,
            infraMongodbRepo: InfraMongodbRepo) =
    Props(new CompanyMaster(infraResource, infraMongodbRepo))

}
