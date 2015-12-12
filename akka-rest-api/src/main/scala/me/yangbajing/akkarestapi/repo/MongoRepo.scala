package me.yangbajing.akkarestapi.repo

import me.yangbajing.akkarestapi.model.ResponseMessage
import org.bson.BsonInt32
import org.mongodb.scala.MongoClient
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.bson.{BsonDocument, BsonObjectId}

import scala.concurrent.{ExecutionContext, Future}

/**
 * Mongo Repo
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
class MongoRepo private(mongoClient: MongoClient, dbName: String, collName: String)(implicit ec: ExecutionContext) {
  def update(doc: Document): Future[Document] = {
    val result =
      for {
        filter <- doc.get[BsonDocument]("filter")
        u <- doc.get[BsonDocument]("update")
      } yield {
        getColl(dbName, collName).updateOne(filter, u).toFuture().flatMap { results =>
          if (results.isEmpty) Future.failed(ResponseMessage(-1, "insert error"))
          else Future.successful(Document(u))
        }
      }

    result getOrElse Future.failed(ResponseMessage(-1, "filter或update不存在"))
  }

  def insert(doc: Document): Future[Document] = {
    val document = if (doc.contains("_id")) doc else (doc.newBuilder += ("_id" -> BsonObjectId())).result()
    getColl(dbName, collName).insertOne(document).toFuture().flatMap { results =>
      if (results.isEmpty) Future.failed(ResponseMessage(-1, "insert error"))
      else Future.successful(document)
    }
  }

  def delete(filter: Document): Future[Document] = {
    getColl(dbName, collName).deleteOne(filter).toFuture().flatMap { results =>
      if (results.isEmpty) Future.failed(ResponseMessage(-1, "delete error"))
      else Future.successful(filter)
    }
  }

  def query(doc: Document): Future[Seq[Document]] = {
    val result =
      for {
        filter <- doc.get[BsonDocument]("filter")
      } yield {
        val cursor = getColl(dbName, collName).find(filter)
        val sortCursor = doc.get[BsonDocument]("sort").map(sort => cursor.sort(sort)).getOrElse(cursor)
        val skipCursor = doc.get[BsonInt32]("skip").map(skip => sortCursor.skip(skip.intValue())).getOrElse(sortCursor)
        val limitCursor = doc.get[BsonInt32]("limit").map(limit => skipCursor.limit(limit.intValue())).getOrElse(skipCursor)
        limitCursor.toFuture()
      }

    result getOrElse Future.failed(ResponseMessage(-1, "filter或update不存在"))
  }

  def getColl(dbName: String, collName: String) = mongoClient.getDatabase(dbName).getCollection(collName)
}

object MongoRepo {
  def apply(dbName: String, collName: String)(implicit ec: ExecutionContext) =
    new MongoRepo(MongoSettings.mongoClient, dbName, collName)
}
