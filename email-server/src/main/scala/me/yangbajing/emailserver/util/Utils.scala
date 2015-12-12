package me.yangbajing.emailserver.util

import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.Timeout

/**
 * 工具
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-10.
 */
object Utils {
  implicit val system = ActorSystem("email-server")
  implicit val materializer = ActorMaterializer()
  implicit val timeout = Timeout(60, TimeUnit.SECONDS)

  val formatterDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

}
