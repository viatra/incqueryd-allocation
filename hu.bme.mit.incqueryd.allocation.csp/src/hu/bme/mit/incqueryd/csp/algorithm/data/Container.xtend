package hu.bme.mit.incqueryd.csp.algorithm.data

class Container extends AbstractContainer{
	@Property
	String name
	
	new(int capacity, int cost, String name) {
		super(capacity, cost)
		this.name = name
	}
}