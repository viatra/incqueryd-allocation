package hu.bme.mit.incqueryd.allocation.test;

import hu.bme.mit.incqueryd.allocation.data.Allocation;
import hu.bme.mit.incqueryd.allocation.data.AllocationProcessInput;
import hu.bme.mit.incqueryd.allocation.util.IncqueryDAllocationClient;
import hu.bme.mit.incqueryd.allocation.util.ReteProcessUtil;
import hu.bme.mit.incqueryd.allocation.util.ReteProcesses;
import hu.bme.mit.incqueryd.csp.stats.StatsUtil;
import hu.bme.mit.incqueryd.csp.util.AllocationOptimizer;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws IOException {
		Map<String, Long> stats = StatsUtil.loadStats("C:\\Users\\Jozsef\\git\\incqueryd-allocation\\hu.bme.mit.incqueryd.queries\\stats\\stats-SignalNeighbor-512.txt");
		AllocationOptimizer allocator = new AllocationOptimizer();
		
		ReteProcesses processes = ReteProcessUtil.createProcessesFromRete("C:\\Users\\Jozsef\\git\\incqueryd-allocation\\hu.bme.mit.incqueryd.queries\\recipes\\SignalNeighbor.rdfiq.recipe", stats);

		Allocation allocation = allocator.allocate(processes.getEdges(), processes.getNodes(), true);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonInString = mapper.writeValueAsString(allocation);
		System.out.println(jsonInString);
		Allocation obj = mapper.readValue(jsonInString, Allocation.class);
		System.out.println(obj);
		
		AllocationProcessInput input = new AllocationProcessInput(processes.getEdges(), processes.getNodes(), true);
		String jsonInString2 = mapper.writeValueAsString(input);
		System.out.println(jsonInString2);
		
		IncqueryDAllocationClient.allocate("C:\\Users\\Jozsef\\git\\incqueryd-allocation\\hu.bme.mit.incqueryd.queries\\recipes\\SignalNeighbor.rdfiq.recipe", "C:\\Users\\Jozsef\\git\\incqueryd-allocation\\hu.bme.mit.incqueryd.queries\\stats\\stats-SignalNeighbor-512.txt", true);
	}

}
