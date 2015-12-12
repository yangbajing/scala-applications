package me.yangbajing.akkarestapi.utils

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.{ContentTypes, HttpCharsets, MediaTypes}
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import akka.stream.Materializer
import org.json4s.jackson.Serialization
import org.json4s.{DefaultFormats, Formats, Serialization}

/**
 * Json Support
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-11-16.
 */
trait JsonSupport {
  implicit val formats = DefaultFormats
  implicit val serialization = Serialization

  implicit def json4sUnmarshallerConverter[A: Manifest](serialization: Serialization, formats: Formats)(implicit mat: Materializer): FromEntityUnmarshaller[A] =
    json4sUnmarshaller(manifest, serialization, formats, mat)

  implicit def json4sUnmarshaller[A: Manifest](implicit serialization: Serialization, formats: Formats, mat: Materializer): FromEntityUnmarshaller[A] =
    Unmarshaller.byteStringUnmarshaller
      .forContentTypes(MediaTypes.`application/json`)
      .mapWithCharset { (data, charset) =>
        val input = if (charset == HttpCharsets.`UTF-8`) data.utf8String else data.decodeString(charset.nioCharset.name)
        serialization.read(input)
      }

  implicit def json4sMarshallerConverter[A <: AnyRef](serialization: Serialization, formats: Formats): ToEntityMarshaller[A] =
    json4sMarshaller(serialization, formats)

  implicit def json4sMarshaller[A <: AnyRef](implicit serialization: Serialization, formats: Formats): ToEntityMarshaller[A] =
    Marshaller.StringMarshaller.wrap(ContentTypes.`application/json`)(serialization.write[A])

}

object JsonSupport extends JsonSupport
