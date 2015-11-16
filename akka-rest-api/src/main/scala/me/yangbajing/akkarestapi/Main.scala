package me.yangbajing.akkarestapi

import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.StrictLogging
import me.yangbajing.akkarestapi.routes.ApiRoute
import me.yangbajing.akkarestapi.service.NewsService

import scala.util.{Failure, Success}

/**
 * Main
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
object Main extends StrictLogging {

  def main(args: Array[String]): Unit = {
    import SystemUtils.{materializer, system}
    import system.dispatcher

    val config = ConfigFactory.load()

    val newsService = NewsService()

    val bindingFuture = Http().bindAndHandle(
      ApiRoute(newsService),
      config.getString("api.network.server"),
      config.getInt("api.network.port"))

    bindingFuture.onComplete {
      case Success(binding) =>
        logger.info(binding.toString)
      case Failure(e) =>
        logger.error(e.getLocalizedMessage, e)
    }
  }

}
