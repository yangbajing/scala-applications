package batchrequest

import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-06-29.
  */
class InfraResource {

  def corpDetail(companyName: String)(implicit ec: ExecutionContext): Future[Option[Company]] = Future {
    TimeUnit.SECONDS.sleep(1)
    println(s"[${LocalDateTime.now()}] 收到查询：$companyName 工商信息付费请求")
    Some(Company(companyName))
  }

}
