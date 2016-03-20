import akka.actor.Actor
import akka.util.Timeout
import scala.concurrent.duration._


/**
  * Created by sahil on 3/18/16.
  */
class ContentAggregator extends Actor {

  implicit val timeout = Timeout(5.seconds)
  var aggregatedMap: Map[String, Int] = Map()

  def receive = {

    case partialMap: Map[String, Int] => aggregatedMap = WordCountSystem.combineMap(partialMap, aggregatedMap)
                                         sender ! 1
    case stop: String => sender ! WordCountSystem.adjustMap(aggregatedMap)
  }

}
