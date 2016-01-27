import akka.actor._
import kamon.trace.Tracer
import kamon.trace.TraceLocal

case class Container(collected: Seq[String])
case object Start

class AdderActor(s: String, next: ActorRef) extends Actor with ActorLogging {
  def receive = {
    case Container(strings) =>
      log.info(s"Adding $s")
      next ! Container(strings :+ s)
  }
}

class ApplicationActor(initial: Int) extends Actor with ActorLogging {
  var count = initial

  def receive = {
    case Start => startProcessing()
    case Container(strings) => {
      log.info(s"Received results: $strings. Missing $count")
      count -= 1
      Tracer.currentContext.finish()

      if (count == 1) {
        self ! PoisonPill
        context.system.shutdown()
      }
    }
  }

  def startProcessing() {
    val add3 = createAdder("s3", self)
    val add2 = createAdder("s2", add3)
    val add1 = createAdder("s1", add2)
    (1 to count) foreach { i =>
      Tracer.withNewContext("adderpipeline") {
        TraceLocal.storeForMdc("txn", "tx" + i)
        log.info("Starting")
        add1 ! Container(Seq.empty)
      }
    }
  }

  def createAdder(s: String, next: ActorRef) = {
    context.actorOf(Props(new AdderActor(s, next)))
  }
}