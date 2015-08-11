package me.yangbajing.emailserver

import java.time.LocalDateTime

import me.yangbajing.emailserver.common.settings.{SettingServer, Setting}
import me.yangbajing.emailserver.domain.{RemoveEmailSender, SendEmail, AddEmailSender}
import me.yangbajing.emailserver.model.EmailSender
import me.yangbajing.emailserver.util.Utils
import play.api.libs.json._

/**
 * Play Json trans Case Class
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
trait JsonImplicits {
  implicit val __localDateTime = new Format[LocalDateTime] {
    override def writes(o: LocalDateTime): JsValue = JsString(o.format(Utils.formatterDateTime))

    override def reads(json: JsValue): JsResult[LocalDateTime] =
      JsSuccess(LocalDateTime.parse(json.as[String], Utils.formatterDateTime))
  }
  implicit val __sendEmailFormats = Json.format[SendEmail]
  implicit val __addEmailSenderFormats = Json.format[AddEmailSender]
  implicit val __removeEmailSenderFormats = Json.format[RemoveEmailSender]
  implicit val __emailSenderFormats = Json.format[EmailSender]
  implicit val __settingServerFormats = Json.format[SettingServer]
  implicit val __settingFormats = Json.format[Setting]
}

object JsonImplicits extends JsonImplicits
