package combinerequest.service

import java.time.LocalDateTime
import java.util.concurrent.{ConcurrentHashMap, TimeUnit}

import combinerequest.domain.Company

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-06-29.
  */
class InfraMongodbRepo {

  def saveCorpDetail(companyName: String, company: Company)(implicit ec: ExecutionContext): Future[Option[Company]] =
    Future {
      TimeUnit.MICROSECONDS.sleep(200)
      println(s"[${LocalDateTime.now()}] 保存公司: $company 成功")
      InfraCompanyDBMock.saveCompany(companyName, company)
    }

  def findCorpDetail(companyName: String)(implicit ec: ExecutionContext): Future[Option[Company]] = Future {
    TimeUnit.MILLISECONDS.sleep(100)
    InfraCompanyDBMock.getCompany(companyName) match {
      case some@Some(_) =>
        println(s"[${LocalDateTime.now()}] 从本地数据库找到公司：$companyName")
        some
      case None =>
        println(s"[${LocalDateTime.now()}] 本地数据库未找到公司：$companyName")
        None
    }
  }

}

object InfraCompanyDBMock {
  private val companies = new ConcurrentHashMap[String, Company]()

  def getCompany(companyName: String) = Option(companies.get(companyName))

  def saveCompany(companyName: String, company: Company) = Option(companies.put(companyName, company))
}
