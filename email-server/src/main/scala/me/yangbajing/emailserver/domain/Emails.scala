package me.yangbajing.emailserver.domain

/**
 * Email Message
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
case class SendEmail(subject: String, to: Seq[String], content: String, userName: Option[String], from: Option[String])

case object GetEmailSenders

case class AddEmailSender(userName: String, password: String, smtp: String, smtpPort: Int, ssl: Boolean)

case class RemoveEmailSender(userName: String)
