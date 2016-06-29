package batchrequest

import akka.actor.{Actor, ActorRef}
import batchrequest.message.{QueryCompany, ReceiveQueryCompanyResult}
import play.api.libs.json.JsValue

import scala.concurrent.Future
import scala.collection.mutable

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-06-29.
  */
trait ForwardCompanyActor extends Actor {

  import ForwardCompanyActor._

  val companyListeners = mutable.Map.empty[String, Set[ActorRef]]

  override def receive = {
    case QueryCompany(companyName, doSender) =>
      val listener = if (doSender == Actor.noSender) sender() else doSender
      registerListener(companyName, listener)

    case ReceiveQueryCompanyResult(companyName, maybeJsValue) =>
      dispatchListeners(companyName, maybeJsValue)
  }

  def performReadTask(companyName: String): Unit = {
    import context.dispatcher
    readFromDB(companyName)
      .flatMap(maybe => if (maybe.isEmpty) readFromInfra(companyName) else Future.successful(maybe))
      .foreach(maybe => self ! ReceiveQueryCompanyResult(companyName, maybe))
  }

  def registerListener(companyName: String, listener: ActorRef): Unit =
    companyListeners.get(companyName) match {
      case Some(actors) =>
        companyListeners.put(companyName, actors + listener)
      case None =>
        companyListeners.put(companyName, Set(listener))
        performReadTask(companyName)
    }

  def dispatchListeners(companyName: String, maybeJsValue: Option[JsValue]): Unit = {
    val maybeListener = companyListeners.get(companyName)
    maybeListener.foreach { listeners =>
      for (listener <- listeners) {
        listener ! maybeJsValue
      }
      companyListeners -= companyName
    }
  }

  val readFromInfra: ReadFromInfra

  val readFromDB: ReadFromDB
}

object ForwardCompanyActor {

  type ReadFromDB = (String) => Future[Option[JsValue]]

  type ReadFromInfra = (String) => Future[Option[JsValue]]

}
