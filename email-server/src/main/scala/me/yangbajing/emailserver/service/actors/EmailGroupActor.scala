package me.yangbajing.emailserver.service.actors

import akka.actor.{Actor, ActorLogging, Props}
import me.yangbajing.emailserver.common.enums.MimeType
import me.yangbajing.emailserver.domain.{EmailSender, SendEmail}
import org.apache.commons.mail.{HtmlEmail, SimpleEmail}

/**
 * 邮箱发送组
 * 每个邮件发送组内串行发送邮件
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-11.
 */
class EmailGroupActor(emailSenders: Map[String, EmailSender]) extends Actor with ActorLogging {

  override def receive: Receive = {
    case se: SendEmail =>
      emailSenders.get(se.sender) match {
        case Some(emailSender) =>
          sendEmail(emailSender, se)

        case None =>
          log.error(s"userName: ${se.sender} not found. $se")
      }
  }


  /**
   * @param emailSender email sender
   * @param se
   */
  private def sendEmail(emailSender: EmailSender, se: SendEmail): Unit = {
    val email =
      if (se.mimeType == MimeType.Text) generateSimpleEmail(emailSender, se)
      else generateHtmlEmail(emailSender, se)
    val sendMsg = email.send()
    log.debug(s"sendEmail $email: $sendMsg")
  }

  private def generateHtmlEmail(emailSender: EmailSender, se: SendEmail) = {
    val email = new HtmlEmail()
    email.setHostName(emailSender.smtp)
    email.setSmtpPort(emailSender.smtpPort)
    email.setAuthentication(emailSender.userName, emailSender.password)
    email.setSSLOnConnect(emailSender.ssl)
    email.addTo(se.to: _*)
    email.setFrom(se.from.getOrElse(emailSender.userName))
    email.setSubject(se.subject)
    email.setHtmlMsg(se.content)
    email
  }

  private def generateSimpleEmail(emailSender: EmailSender, se: SendEmail) = {
    val email = new SimpleEmail()
    email.setHostName(emailSender.smtp)
    email.setSmtpPort(emailSender.smtpPort)
    email.setAuthentication(emailSender.userName, emailSender.password)
    email.setSSLOnConnect(emailSender.ssl)
    email.addTo(se.to: _*)
    email.setFrom(se.from.getOrElse(emailSender.userName))
    email.setSubject(se.subject)
    email.setMsg(se.content)
    email
  }
}

object EmailGroupActor {
  val ACTOR_NAME_PREFIX = "group-"

  def props(emailSenders: Map[String, EmailSender]) = Props(new EmailGroupActor(emailSenders))
}
