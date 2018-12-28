package me.yangbajing.fileupload.model

import java.nio.file.Path

import akka.http.scaladsl.model.HttpHeader
import akka.http.scaladsl.model.Multipart.FormData
import me.yangbajing.fileupload.Constants

import scala.collection.immutable

case class FileInfo(bodyPart: FormData.BodyPart, hash: String, contentLength: Long, startPosition: Long) {
  val validHash: Boolean = Constants.HASH_LENGTH == hash.length
  override def toString: String =
    s"FileInfo(${bodyPart.name}, $hash, $contentLength, $startPosition, ${bodyPart.filename}, ${bodyPart.headers})"
}

object FileInfo {
  val Empty = FileInfo(null, "", 0L, 0L)
}

/**
 * 文件元数据
 * @param hash 文件HASH（sha256）
 * @param size 已上传（bytes）
 * @param localPath 本地存储路径
 */
case class FileMeta(hash: String, size: Long, localPath: Path)

case class FileBO(
    hash: String,
    localPath: Path,
    contentLength: Long,
    filename: Option[String],
    headers: immutable.Seq[HttpHeader]
)
