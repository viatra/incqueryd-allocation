package hu.bme.mit.incqueryd.allocation.data
import scala.collection.JavaConverters._
import scala.beans.BeanProperty

case class AllocationProcessInput(@BeanProperty edges: Array[Array[Int]], nodes: List[Node], @BeanProperty optimizeForCommunication: Boolean) {
  def this(edges: Array[Array[Int]], nodes: java.util.List[Node], optimizeForCommunication: Boolean) = this(edges, nodes.asScala.toList, optimizeForCommunication)
  def getNodes() = {nodes.asJava}
}