package me.yangbajing.emailserver.common.enums

import play.api.libs.json._

/**
 * 邮件格式
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-11.
 */
object MimeType extends Enumeration {
  type MimeType = Value
  val Text = Value("text")
  val Html = Value("html")

  implicit val __mimeTypeFormats = new Format[MimeType] {
    override def writes(o: MimeType.Value): JsValue = JsString(o.toString)

    override def reads(json: JsValue): JsResult[MimeType.Value] = JsSuccess(MimeType.withName(json.as[String]))
  }
}
