package me.yangbajing.emailserver.service

import java.time.LocalDateTime

import akka.actor.{Actor, ActorRefFactory, Props}
import akka.pattern.ask
import akka.util.Timeout
import me.yangbajing.emailserver.domain.{AddEmailSender, GetEmailSenders, RemoveEmailSender, SendEmail}
import me.yangbajing.emailserver.model.EmailSender
import org.apache.commons.mail.SimpleEmail

/**
 * 邮件服务
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
class EmailService(userName: String,
                   initEmailSenders: Seq[AddEmailSender])(implicit actorRefFactory: ActorRefFactory) {
  val emailTask = actorRefFactory.actorOf(EmailTask.props(userName, initEmailSenders))

  def sendEmail(email: SendEmail) = {
    emailTask ! email
  }

  def addEmailSender(sender: AddEmailSender) = {
    emailTask ! sender
  }

  def remoeEmailSender(sender: RemoveEmailSender) = {
    emailTask ! sender
  }

  def getEmailSenders(implicit timeout: Timeout) = {
    (emailTask ? GetEmailSenders).mapTo[Seq[EmailSender]]
  }
}

class EmailTask(defaultUserName: String, initEmailSenders: Seq[AddEmailSender]) extends Actor {

  var emails = Map.empty[String, AddEmailSender] ++ initEmailSenders.map(v => v.userName -> v)

  override def receive: Receive = {
    case SendEmail(subject, to, content, userName, from) =>
      val emailSender = userName.flatMap(emails.get).getOrElse(emails.head._2)
      sendEmail(emailSender, subject, to, content, from)

    case AddEmailSender(account, password, smtp, smtpPort, ssl) =>
      val emailSender = EmailSender(account, password, smtp, smtpPort, ssl, LocalDateTime.now)
      emails += (account -> emailSender)
    // TODO 持久化

    case RemoveEmailSender(account) =>
      emails -= account
    // TODO 从持久化设备

    case GetEmailSenders =>
      sender() ! emails.values.toSeq
  }

  private def sendEmail(emailSender: AddEmailSender,
                        subject: String,
                        to: Seq[String],
                        content: String,
                        from: Option[String]): Unit = {
    val email = new SimpleEmail()
    email.setAuthentication(emailSender.userName, emailSender.password)
    email.setSSLOnConnect(emailSender.ssl)
    email.setSubject(subject)
    email.addTo(to: _*)
    email.setFrom(from.getOrElse(emailSender.userName))
    email.setMsg(content)
    email.send()
  }
}

object EmailTask {
  def props(defaultUserName: String, initEmailSenders: Seq[AddEmailSender]) =
    Props(new EmailTask(defaultUserName, initEmailSenders))
}
