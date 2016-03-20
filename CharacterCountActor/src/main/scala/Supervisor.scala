import akka.actor.Actor
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask

/**
  * Created by sahil on 3/18/16.
  */
class Supervisor extends Actor {

  implicit val timeout = Timeout(5.seconds)

  def receive = {

    case filePath: Path =>
      val line: List[String] = WordCountSystem.fetchContent(filePath.path)
      for (i <- 0 until line.length - 1) {
        Await.result((WordCountSystem.lineCount ? line(i)).mapTo[Int], 5.seconds)
      }

      val res= Await.result((WordCountSystem.aggregator ? "Stop").mapTo[Map[String,Int]], 5.seconds)
      println(res)
  }

}
