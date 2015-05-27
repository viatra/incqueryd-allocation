package hu.bme.mit.incqueryd.csp.cplex;

import hu.bme.mit.incqueryd.csp.algorithm.data.Allocation;
import hu.bme.mit.incqueryd.csp.algorithm.data.Container;
import hu.bme.mit.incqueryd.csp.algorithm.data.Node;
import ilog.concert.IloException;
import ilog.concert.IloIntVarMap;
import ilog.cp.IloCP;
import ilog.opl.IloOplDataSource;
import ilog.opl.IloOplElement;
import ilog.opl.IloOplErrorHandler;
import ilog.opl.IloOplFactory;
import ilog.opl.IloOplModel;
import ilog.opl.IloOplModelDefinition;
import ilog.opl.IloOplModelSource;
import ilog.opl.IloOplSettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CplexAllocationSolver {
	
	private List<Container> containers;
	private List<Node> nodes;
	
	private int[][] solution;
	private Allocation allocation;
	private long communication = 0;
	private long cost = 0;

	public boolean optimizeWithInstances(List<Container> containers, List<Node> nodes, int[][] edges, int[][] overheads, boolean communicationPriority){
		this.containers = containers;
		this.nodes = nodes;
		
		File tempDatFile = null;
		try {
			tempDatFile = File.createTempFile("allocation_cplexdatfile", ".dat");
		} catch (IOException e) {
			System.err.println("Temporary file could not be created, the operation will be stopped:\n" + e.getMessage());
			System.exit(-1);
		}
		
		IloOplFactory.setDebugMode(false);
		IloOplFactory oplFactory = new IloOplFactory();
		IloOplErrorHandler errHandler = oplFactory.createOplErrorHandler();
		IloOplSettings oplSettings = oplFactory.createOplSettings(errHandler);
		IloCP cp = null;
		try {
			cp = oplFactory.createCP();
		} catch (IloException e) {
			System.err.println("Cplex solver could not be used! Terminating allocation.");
			System.exit(-1);
		}
		cp.setOut(null);
		
		String modelSourceName = null;
		CharSequence datFileContent = null;
		if(communicationPriority){
			modelSourceName = "cplex.models/commopt.mod";
			datFileContent = CplexDatFileGenerator.generateCommOptData(nodes, containers, edges, overheads);
		}
		else{
			modelSourceName = "cplex.models/costopt.mod";
			datFileContent = CplexDatFileGenerator.generateCostOptData(nodes, containers);
		}
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(tempDatFile.getAbsolutePath());
			out.append(datFileContent);
			out.flush();
		} catch (FileNotFoundException e) {
			System.exit(-1);
		} finally {
			out.close();
		}
		
		IloOplModelSource modelSource = oplFactory.createOplModelSource(modelSourceName);
		IloOplModelDefinition def = new IloOplModelDefinition(modelSource, oplSettings);
		IloOplModel opl = oplFactory.createOplModel(def, cp);
		IloOplDataSource dataSource = oplFactory.createOplDataSource(tempDatFile.getAbsolutePath());
		opl.addDataSource(dataSource);
		opl.generate();
		
		boolean solved = true;
		try {
			if (cp.solve()) {
				solved = true;
				opl.postProcess();
				
				IloOplElement element = opl.getElement("x");
				IloIntVarMap map = element.asIntVarMap();
				
				int rows = map.getSize();
				int columns = map.getTotalSize() / rows;
				solution = new int[rows][columns];
				for (int i = 1; i <= rows; i++){
					IloIntVarMap row = map.getSub(i);
					for (int j = 1; j <= columns; j++){
						int value = new Double(cp.getValue(row.get(j))).intValue();
						solution[i - 1][j - 1] = value;
					}
				}
				
				if(communicationPriority){
					communication = new Double(cp.getObjValue()).longValue();
				}
				else{
					cost = new Double(cp.getObjValue()).longValue();
				}
				
			} else {
				solved = false;
			}
		} catch (IloException e) {
			System.exit(-1);
		}
		
		oplFactory.end();
		
		if(solved){
			createAllocation();
		}
		
		if(tempDatFile != null){
			tempDatFile.delete();
		}
		
		return solved;
	}
	
	public Allocation getAllocation(){
		return allocation;
	}
	
	private void createAllocation(){
		allocation = new Allocation();
		
		for(int i = 0; i < solution.length; i++){
			int[] row = solution[i];
			for (int j = 0; j < row.length; j++){
				if(row[j] > 0){
					allocation.addNode(containers.get(i).getName(), nodes.get(j));
				}
			}
		}
		
		allocation.setCommunication(communication);
		allocation.setCost(cost);
	}

}
