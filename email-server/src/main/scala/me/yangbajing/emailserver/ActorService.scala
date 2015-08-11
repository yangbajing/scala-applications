package me.yangbajing.emailserver

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

/**
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-11.
 */
trait ActorService {
  implicit val system: ActorSystem

  implicit val materializer: ActorMaterializer
}
