package me.yangbajing.akkarestapi.service

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, ExceptionHandler}

import com.typesafe.scalalogging.StrictLogging
import me.yangbajing.akkarestapi.model.ResponseMessage
import me.yangbajing.akkarestapi.utils.JsonSupport

/**
 * 上下文属性，所有服务都定义在此
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-17.
 */
class ContextProps extends StrictLogging {

  import Directives._
  import me.yangbajing.akkarestapi.utils.JsonSupport._

  val myExceptionHandler = ExceptionHandler {
    case e: ResponseMessage =>
      extractUri { uri =>
        logger.error(s"Request to $uri could not be handled normally")
        complete(StatusCodes.InternalServerError, JsonSupport.serialization.write(e))
      }
//    case _: ArithmeticException =>
//      extractUri { uri =>
//        logger.error(s"Request to $uri could not be handled normally", e)
//        complete(StatusCodes.InternalServerError, "Bad numbers, bad result!!!")
//      }
  }

  val newsService = NewsService()
}
