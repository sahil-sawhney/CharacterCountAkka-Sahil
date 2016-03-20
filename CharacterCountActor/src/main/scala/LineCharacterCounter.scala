import akka.actor.Actor
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask

/**
  * Created by sahil on 3/18/16.
  */
class LineCharacterCounter extends Actor {

  implicit val timeout = Timeout(5.seconds)

  def receive = {

    case line: String =>sender ! Await.result((WordCountSystem.aggregator ? WordCountSystem.count(line)).mapTo[Int], 5.seconds)
  }

}
