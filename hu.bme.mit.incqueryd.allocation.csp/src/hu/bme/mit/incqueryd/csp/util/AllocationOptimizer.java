package hu.bme.mit.incqueryd.csp.util;

import hu.bme.mit.incqueryd.allocation.data.Allocation;
import hu.bme.mit.incqueryd.allocation.data.Node;
import hu.bme.mit.incqueryd.csp.algorithm.data.Container;
import hu.bme.mit.incqueryd.csp.cplex.CplexAllocationSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AllocationOptimizer {
	
	private int[][] overheads;
	
	private final List<Container> containers = new ArrayList<>();
	
	public AllocationOptimizer() {
	}
	
	public synchronized Allocation allocate(int[][] edges, List<Node> nodes, boolean optimizeForCommunication) {	
		getInventory();

		CplexAllocationSolver solver = new CplexAllocationSolver();
		boolean canBeAllocated = solver.optimizeWithInstances(containers, nodes, edges, overheads, optimizeForCommunication);

		Allocation allocation = solver.getAllocation();
		System.out.println(allocation);
		
		if(canBeAllocated) updateCapacities(allocation);

		return canBeAllocated ? allocation : null;
	}
	
	private void getInventory(){
		containers.add(new Container(12*1024, 5, "10.6.17.181"));
		containers.add(new Container(12*1024, 5, "10.6.17.185"));
		containers.add(new Container(8*1024, 3, "10.6.17.189"));
		containers.add(new Container(8*1024, 3, "10.6.17.193"));
		containers.add(new Container(16*1024, 6, "10.6.17.197"));
		
		overheads = new int[][]{
				  { 1, 3, 3, 3, 2 },
				  { 3, 1, 3, 3, 2 },
				  { 3, 3, 1, 3, 2 },
				  { 3, 3, 3, 1, 2 },
				  { 2, 2, 2, 2, 1 }
				};
	}
	
//	public void addContainer(String ip, int memoryCapacity, MemoryUnit unit, int cost){
//		Container container = new Container(getMemoryInMBs(unit,
//				memoryCapacity), cost, ip);
//		
//		containers.add(container);
//		newContainers.add(container);
//	}
	
//	private boolean createOptimizedAllocation(int[][] edges, List<Node> nodes, boolean optimizeForCommunication) {
//		boolean useInstances = !containers.isEmpty();
//
//		if (useInstances) {
//			CplexAllocationSolver solver = new CplexAllocationSolver();
//			boolean canBeAllocated = solver.optimizeWithInstances(containers, nodes, edges, overheads, optimizeForCommunication);
//
//			if (!canBeAllocated) {
//				return false;
//			}
//			Allocation allocation = solver.getAllocation();
//			System.out.println(allocation);
//			updateCapacities(allocation);
//		} else {
//			// TO BE IMPLEMENTED
//		}
//
//		return true;
//	}
	
//	private void createArch(Allocation allocation) throws IOException {
//		ReteRecipe recipe = ArchUtil.loadRecipe(recipeFile);
//
//		final Configuration configuration = ArchFactory.eINSTANCE.createConfiguration();
//		configuration.setConnectionString(inventory.getConnectionString());
//		configuration.getRecipes().add(recipe);
//
//		Map<String, List<Node>> allocations = allocation.getAllocations();
//		Set<String> machines = allocations.keySet();
//
//		boolean firstMachine = true; // This will have the coordinator and the monitoring server
//		for (String ip : machines) {
//			final Machine machine = InfrastructureFactory.eINSTANCE.createMachine();
//			machine.setIp(ip);
//			machine.setName(ip);
//
//			configuration.getMachines().add(machine);
//			if (firstMachine) {
//				configuration.setCoordinatorMachine(machine);
//				configuration.setMonitoringMachine(machine);
//				firstMachine = false;
//			}
//
//			int port_counter = 2552;
//			List<Node> nodesOnMachines = allocations.get(ip);
//			for (Node node : nodesOnMachines) {
//				final Process process = InfrastructureFactory.eINSTANCE.createProcess();
//				process.setPort(port_counter);
//				process.setMemory(node.getSize());
//				process.setTraceInfo(ip + ":" + port_counter);
//				port_counter++;
//
//				machine.getProcesses().add(process);
//
//				InfrastructureMapping infrastructureMapping = ArchFactory.eINSTANCE.createInfrastructureMapping();
//				infrastructureMapping.setProcess(process);
//
//				List<ReteNodeRecipe> reteNodesInProcess = reteNet.getReteNodeRecipesByProcessId(node.getId());
//
//				for (ReteNodeRecipe reteNodeRecipe : reteNodesInProcess) {
//					ReteRole reteRole = ArchFactory.eINSTANCE.createReteRole();
//					reteRole.setNodeRecipe(reteNodeRecipe);
//					infrastructureMapping.getRoles().add(reteRole);
//				}
//
//				configuration.getMappings().add(infrastructureMapping);
//			}
//		}
//
//		ResourceSet resSet = new ResourceSetImpl();
//		Resource resource = resSet.createResource(URI.createFileURI(architectureFile));
//		resource.getContents().add(configuration);
//
//		resource.save(Collections.EMPTY_MAP);
//	}
	
	
	private void updateCapacities(Allocation allocation){
		Map<String, List<Node>> allocations = allocation.getAllocations();
		Set<String> machines = allocations.keySet();
		
		for (String machine : machines) {
			List<Node> nodesOnContainer = allocations.get(machine);
			
			int usedCapacityOnContainer = 0;
			for (Node node : nodesOnContainer) {
				usedCapacityOnContainer += node.getSize();
			}
			
			for (Container container : containers) {
				if(container.getName().equals(machine)){
					container.setMemoryCapacity(container.getMemoryCapacity() - usedCapacityOnContainer);
					break;
				}
			}
		}
	}
	
	
//	private void processInventory() throws IOException {
//		inventory = ArchUtil.loadInventory(new File(inventoryFile));
//
//		MachineSet machineSet = inventory.getMachineSet();
//
//		if (machineSet instanceof TemplateSet) {
//			TemplateSet templateSet = (TemplateSet) machineSet;
//
//			EList<MachineTemplate> machineTemplates = templateSet.getMachineTemplates();
//			overheads = new int[machineTemplates.size()][machineTemplates.size()];
//
//			for (int i = 0; i < machineTemplates.size(); i++) {
//				MachineTemplate machineTemplate = machineTemplates.get(i);
//				ContainerTemplate containerTemplate = new ContainerTemplate(getMemoryInMBs(
//						machineTemplate.getMemoryUnit(), machineTemplate.getMemorySize()), machineTemplate.getCost(),
//						machineTemplate.getIdentifier());
//				containerTemplates.add(containerTemplate);
//				for (int j = 0; j < machineTemplate.getOverheads().size(); j++) {
//					overheads[i][j] = machineTemplate.getOverheads().get(j).intValue();
//				}
//			}
//
//		} else {
//			InstanceSet instanceSet = (InstanceSet) machineSet;
//
//			EList<MachineInstance> machineInstances = instanceSet.getMachineInstances();
//			overheads = new int[machineInstances.size()][machineInstances.size()];
//
//			for (int i = 0; i < machineInstances.size(); i++) {
//				MachineInstance machineInstance = machineInstances.get(i);
//				Container container = new Container(getMemoryInMBs(machineInstance.getMemoryUnit(),
//						machineInstance.getMemorySize()), machineInstance.getCost(), machineInstance.getIp());
//				containers.add(container);
//				for (int j = 0; j < machineInstance.getOverheads().size(); j++) {
//					overheads[i][j] = machineInstance.getOverheads().get(j).intValue();
//				}
//			}
//
//		}
//	}
	
//	private static int getMemoryInMBs(MemoryUnit memUnit, int memorySize) {
//		switch (memUnit) {
//		case MB:
//			return (int)(memorySize * 0.9);
//
//		case GB:
//			return (int)(1024 * memorySize * 0.9);
//
//		default:
//			return 0;
//		}
//	}
}
