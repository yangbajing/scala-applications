package batchrequest

import java.util.concurrent.TimeUnit

import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-06-29.
  */
class InfraResource {

  def corpDetail(companyName: String)(implicit ec: ExecutionContext): Future[Option[JsValue]] = Future {
    TimeUnit.SECONDS.sleep(1)
    Some(Json.obj("companyName" -> companyName))
  }

}
