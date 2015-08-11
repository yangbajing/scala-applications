package me.yangbajing.emailserver.service

import akka.actor.ActorRefFactory
import akka.pattern.ask
import akka.util.Timeout
import me.yangbajing.emailserver.domain.{EmailSender, GetEmailSenders, SendEmail}
import me.yangbajing.emailserver.service.actors.EmailMaster

/**
 * 邮件服务
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
class EmailService(initEmailSenders: Seq[EmailSender])(implicit actorRefFactory: ActorRefFactory) {
  val emailMaster = actorRefFactory.actorOf(EmailMaster.props(initEmailSenders), "email-master")

  def sendEmail(email: SendEmail)(implicit timeout: Timeout) = {
    (emailMaster ? email).mapTo[Either[String, String]]
  }

  def getEmailSenders(implicit timeout: Timeout) = {
    (emailMaster ? GetEmailSenders).mapTo[Seq[EmailSender]]
  }
}
