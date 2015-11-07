package hu.bme.mit.incqueryd.allocation.server

import spray.can.Http
import akka.actor.ActorSystem
import akka.io.IO
import akka.actor.Props

object Boot extends App {
  
  implicit val system = ActorSystem("allocation")
  val handler = system.actorOf(Props[AllocationServiceActor], name = "allocation-service")

  IO(Http) ! Http.Bind(handler, interface = "0.0.0.0", port = 8090)
  
}