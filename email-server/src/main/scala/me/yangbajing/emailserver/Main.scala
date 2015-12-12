package me.yangbajing.emailserver

import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import me.yangbajing.emailserver.common.settings.Settings
import me.yangbajing.emailserver.route.Routes
import me.yangbajing.emailserver.service.{EmailService, MQConsumerService}
import me.yangbajing.emailserver.util.Utils

/**
 * Email Server Main
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
object Main {

  import Utils.{materializer, system}
  import system.dispatcher

  def main(args: Array[String]): Unit = {
    val emailService = new EmailService(Settings.config.emails)
    val mqConsumerService = new MQConsumerService(Settings.config.activemq, emailService)
    val routes = new Routes(emailService, mqConsumerService)
    val bindingFuture = Http().bindAndHandle(routes.routes, Settings.config.server.interface, Settings.config.server.port)

    bindingFuture.onSuccess {
      case ServerBinding(inet) =>
        println("binding to: " + inet)
        mqConsumerService.start()

      case other =>
        println("biding other: " + other)
        mqConsumerService.close()
        system.shutdown()
    }
    bindingFuture.onFailure {
      case e: Exception =>
        mqConsumerService.close()
        system.shutdown()
    }
  }
}
