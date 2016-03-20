import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import scala.concurrent.duration._
import scala.io.Source
/**
  * Created by sahil on 3/18/16.
  */
object WordCountSystem {

  val countingSystem=ActorSystem("WordCount")
  val supervisor=countingSystem.actorOf(Props[Supervisor],"Supervisor")
  val lineCount=countingSystem.actorOf(Props[LineCharacterCounter],"LineCounter")
  val aggregator=countingSystem.actorOf(Props[ContentAggregator],"Aggregator")


  implicit val timeout = Timeout(5.seconds)


  def main(args:Array[String]): Unit ={

    val path="myFile"
    supervisor ! Path(path)
  }

  def fetchContent(path:String):List[String]={

    val content = Source.fromFile(path).mkString
    content.split(".\n").toList
  }

  def count(line:String):Map[String,Int]={

    val fileContentList = line.split("\\W+")
    fileContentList.groupBy(x=>x).map{case(key,value) => (key,value.length)}
  }

  def combineMap(map1:Map[String,Int],map2:Map[String,Int]):Map[String,Int] ={

    val map3:Map[String,Int] = map1 ++ map2
    map3.map{ case (k,v) => (k,v + map1.getOrElse(k,0))}
  }

  def adjustMap(map1:Map[String,Int]):Map[String,Int] ={

    map1.map{case (k,v) =>(k,v-1)}
  }
}
