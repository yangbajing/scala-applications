package sample.respository

import java.time.OffsetDateTime

import SlickProfile.api._
import sample.model.Org

object Schema {
  def apply() = new Schema()
}

class Schema {
  val profile = SlickProfile

  val db = Database.forConfig("sample.datasource")

  def tOrg = TableQuery[TableOrg]
}

class TableOrg(tag: Tag) extends Table[Org](tag, "t_org") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def code = column[Option[String]]("code")

  def name = column[String]("name")

  def contact = column[String]("contact", O.SqlType("text"))

  def status = column[Int]("status", O.Default(1))

  def createdAt = column[OffsetDateTime]("created_at")

  def updatedAt = column[Option[OffsetDateTime]]("update_at")

  def * = (id, code, name, contact, status, createdAt, updatedAt).mapTo[Org]
}
