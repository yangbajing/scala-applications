package me.yangbajing.emailserver.route

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import me.yangbajing.emailserver.JsonImplicits._
import me.yangbajing.emailserver.common.settings.Settings
import me.yangbajing.emailserver.domain.{AddEmailSender, RemoveEmailSender, SendEmail}
import me.yangbajing.emailserver.service.EmailService
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
 * 路由
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
class Routes()(implicit system: ActorSystem, materializer: ActorMaterializer) {
  implicit val timeout = Timeout(60.seconds)

  val emailService = new EmailService(Settings.config.defaultEmail, Settings.config.emails)

  val routes =
    pathPrefix("email") {
      path("send") {
        post {
          entity(as[JsValue].map(_.as[SendEmail])) { sendEmail =>
            complete {
              emailService.sendEmail(sendEmail)
              StatusCodes.OK
            }
          }
        }
      } ~
        path("add") {
          post {
            entity(as[JsValue].map(_.as[AddEmailSender])) { addEmailSender =>
              complete {
                emailService.addEmailSender(addEmailSender)
                StatusCodes.OK
              }
            }
          }
        } ~
        path("remove") {
          post {
            entity(as[JsValue].map(_.as[RemoveEmailSender])) { removeEmailSender =>
              complete {
                emailService.remoeEmailSender(removeEmailSender)
                StatusCodes.custom(200, "", "Remove Email Sender: " + removeEmailSender)
              }
            }
          }
        } ~
        path("users") {
          get {
            onComplete(emailService.getEmailSenders) {
              case Success(emailSenders) => complete(Json.toJson(emailSenders))
              case Failure(e) => complete(StatusCodes.InternalServerError, s"An error occurred: ${e.getMessage}")
            }
          }
        }
    }
}
