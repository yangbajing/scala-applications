package me.yangbajing.akkarestapi.routes

import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer
import me.yangbajing.akkarestapi.model.NewsItem
import me.yangbajing.akkarestapi.service.NewsService

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

/**
 * 新闻 API
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
object NewsApiRoute {

  import akka.http.scaladsl.server.Directives._
  import me.yangbajing.akkarestapi.utils.JsonSupport._

  def apply(newsService: NewsService)(implicit ec: ExecutionContextExecutor, mat: ActorMaterializer) = {
    pathPrefix("news") {
      pathEndOrSingleSlash {
        get {
          parameters(
            'company,
            'method ? "F") { (company, method) =>
            onComplete(newsService.findNews(company, method)) {
              case Success(resp) =>
                complete(resp)
              case Failure(e) =>
                complete(StatusCodes.InternalServerError, e.getLocalizedMessage)
            }
          }
        } ~
          post {
            entity(as[NewsItem]) { item =>
              onComplete(newsService.persistNewsItem(item)) {
                case Success(resp) =>
                  complete(StatusCodes.Created, resp)
                case Failure(e) =>
                  complete(StatusCodes.InternalServerError, e.getLocalizedMessage)
              }
            }
          }
      } ~
        path("status") {
          get {
            onComplete(newsService.getStatus()) {
              case Success(resp) =>
                complete(resp)
              case Failure(e) =>
                complete(StatusCodes.InternalServerError, e.getLocalizedMessage)
            }
          }
        }
    }

  }
}
