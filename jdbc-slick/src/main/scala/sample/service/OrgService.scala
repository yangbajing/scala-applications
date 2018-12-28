package sample.service

import sample.model.{Org, OrgCreateReq}
import sample.respository.{OrgRepository, Schema}

import scala.concurrent.Future

class OrgService(schema: Schema) {

  private val orgRepository= new OrgRepository(schema)

  def create(req: OrgCreateReq): Future[Org] = {
    ???
  }

}
