package hu.bme.mit.incqueryd.allocation.server

import scala.collection.JavaConverters._
import akka.actor.Actor
import spray.http.MediaTypes._
import spray.httpx.SprayJsonSupport.sprayJsonMarshaller
import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller
import spray.json._
import spray.routing.HttpService
import spray.http.HttpEntity
import spray.httpx.unmarshalling.Unmarshaller
import spray.http._
import HttpCharsets._
import hu.bme.mit.incqueryd.allocation.data.Node
import hu.bme.mit.incqueryd.csp.util.AllocationOptimizer
import hu.bme.mit.incqueryd.allocation.data.Allocation
import hu.bme.mit.incqueryd.allocation.data.AllocationProcessInput

object JsonImplicits extends DefaultJsonProtocol {
  implicit val impNode = jsonFormat3(Node)
  implicit val impAllocation = jsonFormat3(AllocationProcessInput)
}

class AllocationServiceActor extends Actor with AllocationService {
  def actorRefFactory = context

  def receive = runRoute(allocationRoute)
}

trait AllocationService extends HttpService {
  val allocationRoute = {

    import JsonImplicits._

    post {
      path("allocation") {
        entity(as[AllocationProcessInput]) { input =>
          complete {
            val allocator: AllocationOptimizer = new AllocationOptimizer
            val allocation: Allocation = allocator.allocate(input.edges , input.nodes.asJava , input.optimizeForCommunication)
            HttpResponse(
              entity = HttpEntity(
                contentType = ContentType(`application/json`, `UTF-8`),
                string = AllocationSerializer.allocationToJson(allocation)))
          }
        }
      }
    }
  }
}