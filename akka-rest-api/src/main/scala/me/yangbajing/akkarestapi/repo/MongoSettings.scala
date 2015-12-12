package me.yangbajing.akkarestapi.repo

import org.mongodb.scala.MongoClient

/**
 * Mongo Settings
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
object MongoSettings {
  val mongoClient = MongoClient("mongodb://localhost")
  //  val database = mongoClient.getDatabase("news_db")

  //  def insert(dbName: String, collName: String, doc: Document) = {
  //    getCollection(dbName, collName).insertOne(doc).toFuture()
  //  }

  //  def findOne(dbName: String, collName: String, query: Document)

  def getCollection(dbName: String, collName: String) = getDatabase(dbName).getCollection(collName)

  def getDatabase(dbName: String) = mongoClient.getDatabase(dbName)
}
