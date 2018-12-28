package sample.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, MustMatchers, OptionValues, WordSpec}
import sample.model.{Org, OrgCreateReq}
import akka.http.scaladsl.server.Directives._
import sample.respository.OrgRepository
import sample.service.OrgService

class OrgRouteTest extends WordSpec with BeforeAndAfterAll with ScalatestRouteTest with MustMatchers with OptionValues with ScalaFutures {

  private var orgIds: Set[Int] = Set()
  private val schema = Schema()
  private val orgService = new OrgService()
  private val route = new OrgRoute(orgService).route

  "OrgRoute" should {
    import sample.util.JacksonSupport._

    var org: Org = null

    "create" in {
      val req = OrgCreateReq(Some("000001"), "测试组织", None)
      Post("/org/item", req) ~> route ~> check {
        status mustBe StatusCodes.Created
        org = responseAs[Org]
        orgIds += org.id
        org.id must be > 0
        org.parent mustBe None
        org.updatedAt mustBe None
      }
    }

    "get" in {
      pending
    }

    "pageRoute" in {
      pending
    }

    "updateRoute" in {
      pending
    }

    "remoteRoute" in {
      pending
    }
  }

  private def cleanup() {
    val orgRepository = new OrgRepository()
    orgRepository.removeByIds(orgIds).futureValue
  }

  override def afterAll() {
    cleanup()
    super.afterAll()
  }

}
