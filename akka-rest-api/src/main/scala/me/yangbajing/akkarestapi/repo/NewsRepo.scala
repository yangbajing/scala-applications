package me.yangbajing.akkarestapi.repo

import me.yangbajing.akkarestapi.model.{NewsItem, ResponseMessage}
import me.yangbajing.akkarestapi.utils.JsonSupport
import org.mongodb.scala.bson.collection.immutable.Document

import scala.concurrent.{ExecutionContext, Future}

/**
 * News Repo
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
class NewsRepo() {

  import JsonSupport._

  val newsColl = MongoSettings.getCollection("news_db", "news")

  def insert(item: NewsItem)(implicit ec: ExecutionContext): Future[ResponseMessage] = {
    val doc = Document(serialization.write(item))
    newsColl.insertOne(doc).toFuture().map { completes =>
      if (completes.isEmpty)
        throw ResponseMessage(-1, "not found")
      ResponseMessage()
    }
  }

}
