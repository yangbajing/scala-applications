package batchrequest

import java.util.concurrent.TimeUnit

import play.api.libs.json.{JsObject, JsValue, Json}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-06-29.
  */
class InfraMongodbRepo {

  def saveCorpDetail(companyName: String, jsObject: JsObject)(implicit ec: ExecutionContext): Future[Int] = Future {
    TimeUnit.MICROSECONDS.sleep(200)
    1
  }

  def findCorpDetail(companyName: String)(implicit ec: ExecutionContext): Future[Option[JsValue]] = Future {
    TimeUnit.MILLISECONDS.sleep(100)
    Some(Json.obj("companyName" -> companyName))
  }

}
