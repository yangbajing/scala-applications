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
    Setting(_server(c.getConfig("server")), emails, _activemq(c.getConfig("activemq")))
  }

  private def _emailSender(c: Config): EmailSender = {
    EmailSender(c.getString("userName"), c.getString("password"), c.getString("smtp"), c.getInt("smtpPort"),
      c.getBoolean("ssl"), Try(c.getBoolean("default")).getOrElse(false), Try(c.getString("group")).getOrElse(""))
  }

  private def _server(c: Config) = SettingServer(c.getString("interface"), c.getInt("port"))

  private def _activemq(c: Config) = SettingActivemq(c.getString("url"),
    Try(c.getString("userName")).toOption, Try(c.getString("password")).toOption,
    c.getString("emailQueueName"))
}

case class SettingActivemq(url: String, userName: Option[String], password: Option[String], emailQueueName: String)

case class SettingServer(interface: String, port: Int)

case class Setting(server: SettingServer, emails: Seq[EmailSender], activemq: SettingActivemq)
