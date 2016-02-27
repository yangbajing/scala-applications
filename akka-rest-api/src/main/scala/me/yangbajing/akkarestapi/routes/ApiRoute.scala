package me.yangbajing.akkarestapi.routes

import akka.http.scaladsl.server.Directives._

import akka.stream.Materializer
import me.yangbajing.akkarestapi.service.ContextProps

import scala.concurrent.ExecutionContext

/**
 * Api Route
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
object ApiRoute {

  def apply(props: ContextProps)(implicit ec: ExecutionContext, mat: Materializer) =
    handleExceptions(props.myExceptionHandler) {
      pathPrefix("api") {
        NewsApiRoute(props) ~
          MongoApiRoute(props)
      }
    }

}
