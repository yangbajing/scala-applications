package me.yangbajing.emailserver.service

import akka.actor.{Actor, ActorRefFactory, Props}
import akka.pattern.ask
import akka.util.Timeout
import me.yangbajing.emailserver.common.settings.Settings
import me.yangbajing.emailserver.domain.{EmailSender, GetEmailSenders, SendEmail}
import org.apache.commons.mail.SimpleEmail

/**
 * 邮件服务
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
class EmailService(initEmailSenders: Seq[EmailSender])(implicit actorRefFactory: ActorRefFactory) {
  val emailTask = actorRefFactory.actorOf(EmailTask.props(initEmailSenders))

  def sendEmail(email: SendEmail) = {
    emailTask ! email
  }

  def getEmailSenders(implicit timeout: Timeout) = {
    (emailTask ? GetEmailSenders).mapTo[Seq[EmailSender]]
  }
}

class EmailTask(initEmailSenders: Seq[EmailSender]) extends Actor {
  val defaultEmail = Settings.config.emails.find(_.default).getOrElse(Settings.config.emails.head)

  @volatile
  var emails = Map.empty[String, EmailSender] ++ initEmailSenders.map(v => v.userName -> v)

  override def receive: Receive = {
    case SendEmail(subject, to, content, userName, fromOpt) =>
      val emailSender = userName.flatMap(emails.get).getOrElse(defaultEmail)
      sendEmail(emailSender, subject, to, content, fromOpt)

    case GetEmailSenders =>
      sender() ! emails.values.toSeq
  }

  private def sendEmail(emailSender: EmailSender,
                        subject: String,
                        to: Seq[String],
                        content: String,
                        fromOpt: Option[String]): Unit = {
    val email = new SimpleEmail()
    email.setHostName(emailSender.smtp)
    email.setSmtpPort(emailSender.smtpPort)
    email.setAuthentication(emailSender.userName, emailSender.password)
    email.setSSLOnConnect(emailSender.ssl)
    email.addTo(to: _*)
    email.setFrom(fromOpt.getOrElse(emailSender.userName))
    email.setSubject(subject)
    email.setMsg(content)
    email.send()
  }
}

object EmailTask {
  def props(initEmailSenders: Seq[EmailSender]) = Props(new EmailTask(initEmailSenders))
}
