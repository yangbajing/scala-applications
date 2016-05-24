import java.nio.file._

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.Await
import scala.concurrent.duration._

object Demo extends App {
  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  val client = AhcWSClient()

  val paramsList =
    """曹某某 4310231982xxxxxx38 158xxxx7233 6217xxxxxxxxxx95922
      |陈某 4503041989xxxxxx14 186xxxx1221 6229xxxxxxxxx79419
      |雷某某 6121021979xxxxxx19 186xxxxx758 6217xxxxxxxxxx75215
      |  季某某 3101151983xxxxxx39     139xxxx4665 6226xxxxxxx63987
      |何某某 4101811989xxxxxx14 131xxxx2515 6127xxxxxxxxxx01601
      |熊某某 5125281981xxxxxx70 188xxxx7391 6230xxxxxxxxxx61491
      |  罗某某 4305811990xxxxxx94 158xxxx6035 6212xxxxxxxxxx20908  
      |吴某某 5002371986xxxxxx68 188xxxx8725 6228xxxxxxxxxx16476
      |罗某 4409811991xxxxxx11 135xxxx3967 6212xxxxxxxxxx62918   
      |许某 3607351997xxxxxx37 181xxxx7357 6228xxxxxxxxxx41278""".stripMargin
      .split("\n")
      .map { line =>
        //("external", "true") :: (List("personName", "idCard", "tel", "bankCard") zip line.trim.split("""[ ]+"""))
        Seq("personName", "idCard", "tel", "bankCard") zip line.trim.split("""[ ]+""")
      }
  //paramsList.foreach(println)

  def send(params: Seq[(String, String)]) = {
    val future = client
      .url("http://localhost:8080/api/integration/person/report")
      .withQueryString(params: _*)
      .get()
    val resp = Await.result(future, 10.seconds)
    resp.body
  }

  val results = paramsList.map(params => send(params))
  Files.write(Paths.get("demo.txt"), results.mkString("\n").getBytes("UTF-8"))

  client.close()
  Await.result(system.terminate(), 10.seconds)
}

