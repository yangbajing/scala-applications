package me.yangbajing.emailserver.common.settings

import me.yangbajing.emailserver.JsonImplicits._
import me.yangbajing.emailserver.domain.AddEmailSender
import play.api.libs.json.Json

import scala.io.Source

/**
 * Email Server Setting
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-11.
 */
object Settings {
  val configFile = Option(System.getProperty("emailserver.config"))
  val config = {
    val source = configFile.map(Source.fromFile(_, "UTF-8")).getOrElse(
      Source.fromInputStream(Thread.currentThread().getContextClassLoader.getResourceAsStream("emailserver.json"), "UTF-8"))
    try {
      Json.parse(source.mkString).as[Setting]
    } finally {
      source.close()
    }
  }
}

case class Setting(server: SettingServer, defaultEmail: String, emails: Seq[AddEmailSender])

case class SettingServer(interface: String, port: Int)
