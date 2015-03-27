package hu.bme.mit.incqueryd.csp.cplex

import java.util.List
import hu.bme.mit.incqueryd.csp.algorithm.data.Node
import hu.bme.mit.incqueryd.csp.algorithm.data.Container

class CplexDatFileGenerator {
	
	static def CharSequence generateCostOptData(List<Node> nodes, List<Container> containers)'''
	n = «nodes.length»;
	processes = [«FOR node : nodes»«node.size»,«ENDFOR»];
	
	m = «containers.length»;
	capacities = [«FOR container : containers»«container.memoryCapacity»,«ENDFOR»];
	costs = [«FOR container : containers»«container.cost»,«ENDFOR»];
	'''
	
	static def CharSequence generateCommOptData(List<Node> nodes, List<Container> containers, int[][] edges, int[][] overheads)'''
	n = «nodes.length»;
	processes = [«FOR node : nodes»«node.size»,«ENDFOR»];
	edges = [
		«FOR rows : edges»
		[«FOR edge : rows»«edge»,«ENDFOR»],
		«ENDFOR»
	];
	
	m = «containers.length»;
	capacities = [«FOR container : containers»«container.memoryCapacity»,«ENDFOR»];
	overheads = [
		«FOR rows : overheads»
		[«FOR overhead : rows»«overhead»,«ENDFOR»],
		«ENDFOR»
	];
	'''
	
}