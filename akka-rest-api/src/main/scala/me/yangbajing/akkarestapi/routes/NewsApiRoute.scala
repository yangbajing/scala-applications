package me.yangbajing.akkarestapi.routes

import akka.http.scaladsl.model.StatusCodes
import akka.stream.Materializer
import me.yangbajing.akkarestapi.model.NewsItem
import me.yangbajing.akkarestapi.service.ContextProps

import scala.concurrent.ExecutionContext

/**
 * 新闻 API
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
object NewsApiRoute {

  import akka.http.scaladsl.server.Directives._
  import me.yangbajing.akkarestapi.utils.JsonSupport._

  def apply(props: ContextProps)(implicit ec: ExecutionContext, mat: Materializer) = {
    pathPrefix("news") {
      pathEndOrSingleSlash {
        get {
          parameters(
            'company,
            'method ? "F") { (company, method) =>
            onSuccess(props.newsService.findNews(company, method)) { result =>
              complete(result)
            }
          }
        } ~
          post {
            entity(as[NewsItem]) { item =>
              onSuccess(props.newsService.persistNewsItem(item)) { result =>
                complete(StatusCodes.Created, result)
              }
            }
          }
      } ~
        path("status") {
          get {
            onSuccess(props.newsService.getStatus()) { result =>
              complete(result)
            }
          }
        }
    }

  }

}
