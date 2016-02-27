import com.wacai.config.annotation._

import scala.language.reflectiveCalls
import scala.concurrent.duration._

@conf
trait Config {
  val server = new {
    val host = "yangbajing.me"
    val port = 12306
  }

  val socket = new {
    val timeout = 3.seconds
    val buffer = 1024 * 64L
  }

  val client = "yangbajing"
}

object Configs extends Config {
  val host = server.host
}

