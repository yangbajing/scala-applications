package me.yangbajing.fileupload.service

import java.nio.file._
import java.security.MessageDigest
import java.util.{Collections, Objects}

import akka.actor.ActorSystem
import akka.http.scaladsl.model.Multipart
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.{FileIO, Sink}
import com.typesafe.scalalogging.StrictLogging
import me.yangbajing.fileupload.Constants
import me.yangbajing.fileupload.model.{FileBO, FileInfo, FileMeta}
import me.yangbajing.fileupload.util.{FileUtils, Utils}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class FileServiceImpl(
    val system: ActorSystem,
    implicit val mat: ActorMaterializer
) extends FileService
    with StrictLogging {

  override def progressByHash(hash: String): Future[FileMeta] = {
    require(Objects.nonNull(hash) && hash.nonEmpty, "hash 不能为空。")
    FileUtils.getFileMeta(hash) match {
      case Some(meta) => Future.successful(meta)
      case None       => Future.failed(new IllegalAccessException(s"文件：$hash 不存在或不可访问。"))
    }
  }

  override def handleUpload(formData: Multipart.FormData): Future[Seq[FileBO]] = {
    formData.parts
//      .groupBy(Constants.FILE_PART_MAX, part => part.name.split('.').head)
//      .async
//      .foldAsync[FileInfo](FileInfo.Empty)((fileInfo, part) => mergeBodyPart(fileInfo, part))
//      .mergeSubstreams
      .map { part =>
        val (hash, contentLength, startPosition) = part.name.split('.') match {
          case Array(a, b, c) => (a, b, c)
          case Array(a, b)    => (a, b, "0")
          case Array(a)       => (a, "0", "0")
          case _              => ("", "0", "0")
        }
        FileInfo(part, hash, contentLength.toLong, startPosition.toLong)
      }
      .log("fileInfo", info => logger.debug(s"fileInfo: $info"))
      .mapAsync(Constants.FILE_PART_MAX)(processFile)
      .runWith(Sink.seq)
  }

  private def mergeBodyPart(fileInfo: FileInfo, part: Multipart.FormData.BodyPart): Future[FileInfo] = {
    import system.dispatcher
    part.name.split('.') match {
      case Array(_) =>
        Future.successful(fileInfo.copy(bodyPart = part))
      case Array(_, "hash") =>
        part.entity
          .toStrict(1.second)
          .flatMap { entity =>
            val hash = entity.data.utf8String
            if (Constants.HASH_LENGTH != hash.length)
              Future.failed(new IllegalArgumentException("hash值应为64位sha256的16进制字符串"))
            else
              Future.successful(fileInfo.copy(hash = hash.toLowerCase))
          }
      case Array(_, "contentLength") =>
        part.entity.toStrict(1.second).map(entity => fileInfo.copy(contentLength = entity.data.utf8String.toLong))
      case Array(_, "startPosition") =>
        part.entity.toStrict(1.second).map(entity => fileInfo.copy(startPosition = entity.data.utf8String.toLong))
      case _ =>
        Future.failed(new IllegalArgumentException(s"未知的FormData字段：${part.name}"))
    }
  }

  /**
   * sha存在，判断文件是否已上传？
   * startPosition存在，正从startPosition位置开始上传
   * @param fileInfo
   * @return
   */
  private def processFile(fileInfo: FileInfo)(implicit mat: Materializer): Future[FileBO] = {
    import system.dispatcher
    val bodyPart = fileInfo.bodyPart
    val hash = fileInfo.hash
    FileUtils.getFileMeta(hash) match {
      case Some(fileMeta) if fileInfo.contentLength == fileMeta.size => // 已上传完成
        Future.successful(FileBO(hash, fileMeta.localPath, fileMeta.size, bodyPart.filename, bodyPart.headers))
      case _ =>
        FileUtils.uploadFile(fileInfo)
    }
  }

}
