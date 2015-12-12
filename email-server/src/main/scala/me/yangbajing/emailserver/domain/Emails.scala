package me.yangbajing.emailserver.domain

import me.yangbajing.emailserver.common.enums.MimeType

/**
 * Email Message
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
case class SendEmail(sender: String, subject: String, to: Seq[String], content: String, from: Option[String],
                     mimeType: MimeType.MimeType)

case object GetEmailSenders

case class EmailSender(userName: String, password: String, smtp: String, smtpPort: Int, ssl: Boolean, group: String)
