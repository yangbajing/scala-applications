package combinerequest.message

import akka.actor.{Actor, ActorRef}
import combinerequest.domain.Company

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-06-29.
  */
case class QueryCompany(companyName: String, doSender: ActorRef = Actor.noSender)

case class ReceiveQueryCompanyResult(companyName: String, corpDetail: Option[Company])

sealed trait GetCompanyMessage {
  val companyName: String
}

/**
  * 获取工商消息
  *
  * @param companyName 公司名
  */
case class GetCorpDetail(companyName: String) extends GetCompanyMessage
