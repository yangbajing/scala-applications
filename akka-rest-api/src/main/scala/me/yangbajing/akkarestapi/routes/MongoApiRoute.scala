package me.yangbajing.akkarestapi.routes

import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer
import me.yangbajing.akkarestapi.repo.MongoRepo
import org.mongodb.scala.bson.collection.immutable.Document

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

/**
 * Mongo Api
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
object MongoApiRoute {

  import akka.http.scaladsl.server.Directives._
  import me.yangbajing.akkarestapi.utils.DocumentSupport._

  def apply()(implicit ec: ExecutionContextExecutor, mat: ActorMaterializer) = {
    path("mongo" / Segment / Segment / Segment) { (dbName, collName, method) =>
      post {
        entity(as[Document]) { doc =>
          val mongoRepo = MongoRepo(dbName, collName)
          onComplete(method match {
            case "_update" =>
              mongoRepo.update(doc).map(updateDoc => complete(updateDoc))
            case "_insert" =>
              mongoRepo.insert(doc).map(doc => complete(StatusCodes.Created, doc))
            case "_query" =>
              mongoRepo.query(doc).map(docs => complete(docs))
            case "_delete" =>
              mongoRepo.delete(doc).map(filterDoc => complete(filterDoc))
          }) {
            case Success(response) =>
              response
            case Failure(e) =>
              e.printStackTrace()
              complete(StatusCodes.InternalServerError, e.getLocalizedMessage)
          }
        }
      }
    }
  }

}
