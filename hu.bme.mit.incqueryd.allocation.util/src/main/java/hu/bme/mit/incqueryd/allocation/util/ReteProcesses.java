package hu.bme.mit.incqueryd.allocation.util;


import hu.bme.mit.incqueryd.allocation.data.Node;

import java.util.List;

public class ReteProcesses {
	
	private int[][] edges;
	private List<Node> nodes;
	
	public ReteProcesses(int[][] edges, List<Node> nodes) {
		this.edges = edges;
		this.nodes = nodes;
	}
	
	public int[][] getEdges() {
		return edges;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}

}
