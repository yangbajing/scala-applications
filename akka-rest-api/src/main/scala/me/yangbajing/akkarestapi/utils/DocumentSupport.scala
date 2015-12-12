package me.yangbajing.akkarestapi.utils

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.{ContentType, HttpCharsets, MediaTypes}
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import akka.stream.Materializer
import org.mongodb.scala.bson.collection.immutable.Document

/**
 * MongoDB Document Akka Http (un)marshall支持
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
trait DocumentSupport {
  implicit def documentUnmarshaller(implicit mat: Materializer): FromEntityUnmarshaller[Document] =
    Unmarshaller.byteStringUnmarshaller
      .forContentTypes(MediaTypes.`application/json`)
      .mapWithCharset { (data, charset) =>
        val input = if (charset == HttpCharsets.`UTF-8`) data.utf8String else data.decodeString(charset.nioCharset.name)
        Document(input)
      }

  implicit val documentMarshaller: ToEntityMarshaller[Document] =
    Marshaller.StringMarshaller.wrap(ContentType(MediaTypes.`application/json`, HttpCharsets.`UTF-8`))(v => v.toJson())
  implicit val seqDocumentMarshaller: ToEntityMarshaller[Seq[Document]] =
    Marshaller.StringMarshaller.wrap(ContentType(MediaTypes.`application/json`, HttpCharsets.`UTF-8`))(vs => vs.map(_.toJson()).mkString("[", ",", "]"))

}

object DocumentSupport extends DocumentSupport