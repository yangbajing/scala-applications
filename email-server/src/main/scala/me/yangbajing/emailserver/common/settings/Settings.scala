package me.yangbajing.emailserver.common.settings

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}
import me.yangbajing.emailserver.domain.EmailSender

import scala.collection.JavaConverters._
import scala.util.Try

/**
 * Email Server Setting
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-11.
 */
object Settings {
  val config = {
    val c =
      Option(System.getProperty("application.file")).map(file => ConfigFactory.parseFile(new File(file)))
        .getOrElse(ConfigFactory.load()).getConfig("emailserver")
    _setting(c)
  }

  private def _setting(c: Config) = {
    val emailConf = c.getConfig("emails")
    val emails = emailConf.entrySet().asScala.toStream
      .filter(entry => entry.getKey.endsWith(".userName"))
      .map(_.getKey.replace(".userName", ""))
      .map(key => _emailSender(emailConf.getConfig(key)))
      .toVector
    new Setting(_server(c.getConfig("server")), emails)
  }

  private def _emailSender(c: Config): EmailSender = {
    new EmailSender(c.getString("userName"), c.getString("password"), c.getString("smtp"), c.getInt("smtpPort"),
      c.getBoolean("ssl"), Try(c.getBoolean("default")).getOrElse(false))
  }

  private def _server(c: Config) = new SettingServer(c.getString("interface"), c.getInt("port"))
}

case class Setting(server: SettingServer, emails: Seq[EmailSender])

case class SettingServer(interface: String, port: Int)
