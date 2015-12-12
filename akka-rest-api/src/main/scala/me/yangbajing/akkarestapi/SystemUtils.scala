package me.yangbajing.akkarestapi

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

/**
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
object SystemUtils {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
}
