package me.yangbajing.emailserver.route

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import me.yangbajing.emailserver.ActorService
import me.yangbajing.emailserver.JsonImplicits._
import me.yangbajing.emailserver.common.settings.Settings
import me.yangbajing.emailserver.domain.SendEmail
import me.yangbajing.emailserver.service.{MQConsumerService, EmailService}
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
 * 路由
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
class Routes(val emailService: EmailService,
             val mQConsumerService: MQConsumerService)(implicit val system: ActorSystem, val materializer: ActorMaterializer) extends ActorService {
  implicit val timeout = Timeout(60.seconds)

  val routes =
    pathPrefix("email") {
      path("send") {
        post {
          entity(as[JsValue].map(_.as[SendEmail])) { sendEmail =>
            onComplete(emailService.sendEmail(sendEmail)) {
              case Success(value) =>
                value match {
                  case Right(msg) => complete(msg)
                  case Left(msg) => complete(StatusCodes.Forbidden, msg)
                }

              case Failure(e) => complete(StatusCodes.InternalServerError, "SendEmail an error occurred: " + e.getMessage)
            }
          }
        }
      } ~
        path("users") {
          get {
            onComplete(emailService.getEmailSenders) {
              case Success(emailSenders) => complete(Json.toJson(emailSenders))

              case Failure(e) => complete(StatusCodes.InternalServerError, "An error occurred: " + e.getMessage)
            }
          }
        }
    }
}
