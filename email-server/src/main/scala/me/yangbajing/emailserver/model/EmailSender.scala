package me.yangbajing.emailserver.model

import java.time.LocalDateTime

/**
 * EmailSender
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
case class EmailSender(userName: String,
                       password: String,
                       smtp: String,
                       smtpPort: Int,
                       ssl: Boolean,
                       createdAt: LocalDateTime) {

}
