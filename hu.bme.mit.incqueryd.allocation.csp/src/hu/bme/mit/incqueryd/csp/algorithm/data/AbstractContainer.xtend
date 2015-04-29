package hu.bme.mit.incqueryd.csp.algorithm.data

abstract class AbstractContainer {
	@Property
	int memoryCapacity
	@Property
	int cost
	
	new(int capacity, int cost) {
		this.memoryCapacity = capacity
		this.cost = cost
	}
}