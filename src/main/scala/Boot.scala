import akka.actor._
import kamon.Kamon

object Boot extends App {
    Kamon.start()
  implicit val system = ActorSystem()
  val a = system.actorOf(Props(new ApplicationActor(10)))
  a ! Start
}