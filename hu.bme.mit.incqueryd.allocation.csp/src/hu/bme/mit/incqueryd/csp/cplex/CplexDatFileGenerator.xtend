package hu.bme.mit.incqueryd.csp.cplex

import hu.bme.mit.incqueryd.csp.algorithm.data.Container
import java.util.List

class CplexDatFileGenerator {

	// Xtend sajnos nem kezeli jól a Scala-s osztályokat...
	static def CharSequence generateCostOptData(int processesLength, String processes, List<Container> containers) '''
		n = «processesLength»;
		processes = [«processes»];
		
		m = «containers.length»;
		capacities = [«FOR container : containers»«container.memoryCapacity»,«ENDFOR»];
		costs = [«FOR container : containers»«container.cost»,«ENDFOR»];
	'''

	static def CharSequence generateCommOptData(int processesLength, String processes, List<Container> containers, int[][] edges,
		int[][] overheads) '''
		n = «processesLength»;
		processes = [«processes»];
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
