package me.yangbajing.fileupload.controller

import java.nio.file.{Files, Paths}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentType, HttpEntity, HttpMethods, HttpRequest, MediaTypes, Multipart, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Attributes}
import akka.stream.scaladsl.{FileIO, Source}
import akka.testkit.TestKit
import com.fasterxml.jackson.databind.node.ObjectNode
import helloscala.common.util.DigestUtils
import me.yangbajing.fileupload.io.CustomFileSource
import org.scalatest.{FunSuiteLike, MustMatchers}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Milliseconds, Seconds, Span}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
* 断点上传文件
  */
class FileRouteTest extends TestKit(ActorSystem("file-demo")) with FunSuiteLike with MustMatchers with ScalaFutures {
  implicit private val mat = ActorMaterializer()

  val file          = Paths.get("/opt/Videos/VID_20190322_104457.mp4")
  val hash          = DigestUtils.sha256HexFromPath(file)
  val fileLength    = Files.size(file)
  var startPosition = 0L

  test("upload/continue 大文件") {
    val truncateSize = fileLength / 2
    val bodyPart = Multipart.FormData.BodyPart(
      s"$hash.$fileLength.0",
      HttpEntity.Default(
        ContentType(MediaTypes.`video/mp4`),
        fileLength,
        Source
          .fromGraph(new CustomFileSource(file, 8129, 0L, truncateSize))
          .withAttributes(Attributes(Attributes.Name("file")))),
      Map("filename" → file.getFileName.toString))
    val formData = Multipart.FormData(bodyPart)
    val request = HttpRequest(
      HttpMethods.POST,
      "https://filetest.hongkazhijia.com/file/ihongka_files/upload/continue",
      entity = formData.toEntity())
    val responseF = Http().singleRequest(request)
    val response  = Await.result(responseF, Duration.Inf)
    println(s"response: ${response.entity}")
  }

  test("upload/continue 大文件上传进度") {
    import me.yangbajing.fileupload.util.JacksonSupport._
    val request  = HttpRequest(HttpMethods.GET, s"https://filetest.hongkazhijia.com/file/ihongka_files/progress/$hash")
    val response = Http().singleRequest(request).futureValue
    response.status mustBe StatusCodes.OK
    val result = Unmarshal(response.entity).to[ObjectNode].futureValue
    println(result)
    startPosition = result.get("size").asLong()
  }

  test("upload/continue 断点续传") {
    import me.yangbajing.fileupload.util.JacksonSupport._
    val bodyPart = Multipart.FormData.BodyPart(
      s"$hash.$fileLength.$startPosition",
      HttpEntity
        .Default(ContentType(MediaTypes.`video/mp4`), Files.size(file), FileIO.fromPath(file, 8192, startPosition)))
    val formData = Multipart.FormData(bodyPart)
    val request = HttpRequest(
      HttpMethods.POST,
      "https://filetest.hongkazhijia.com/file/ihongka_files/upload/continue",
      entity = formData.toEntity())
    val responseF = Http().singleRequest(request)
    val response  = Await.result(responseF, Duration.Inf)
    response.status mustBe StatusCodes.OK
    val result = Unmarshal(response.entity).to[ObjectNode].futureValue
    println(result)
    result.get("_id").asText() mustBe hash
    result.get("size").asLong() mustBe fileLength
  }

  implicit override def patienceConfig: PatienceConfig = PatienceConfig(Span(30, Seconds), Span(50, Milliseconds))
}
