package me.yangbajing.emailserver.app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import me.yangbajing.emailserver.route.Routes

/**
 * Email Server Main
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
object Main {
  implicit val system = ActorSystem("email-server")
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    val routes = new Routes()
    Http().bindAndHandle(routes.routes, "localhost", 9999)
  }
}
