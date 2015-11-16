package me.yangbajing.akkarestapi.routes

import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import me.yangbajing.akkarestapi.service.NewsService

import scala.concurrent.ExecutionContextExecutor

/**
 * Api Route
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
object ApiRoute {

  def apply(newsService: NewsService)(implicit ec: ExecutionContextExecutor, mat: ActorMaterializer) = {
    pathPrefix("api") {
      NewsApiRoute(newsService) ~
        MongoApiRoute()
    }
  }

}
