package sample.route
import akka.http.scaladsl.server.Directives._
import sample.model.OrgCreateReq
import sample.service.OrgService

class OrgRoute(orgService: OrgService) {

  def route= pathPrefix("route") {
    createRoute
  }

  def createRoute = (path("item") & post) {
    import sample.util.JacksonSupport._
    entity(as[OrgCreateReq]) { req =>
      onSuccess(orgService.create(req)) { resp =>
        complete(resp)
      }
    }
  }
}
