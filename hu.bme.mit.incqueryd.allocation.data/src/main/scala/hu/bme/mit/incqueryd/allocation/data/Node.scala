package hu.bme.mit.incqueryd.allocation.data

import scala.beans.BeanProperty

case class Node (@BeanProperty id: Int, @BeanProperty name: String, @BeanProperty size: Int) {
	def this() = this(-1, "", 0)
}
