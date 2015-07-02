package org.eclipse.viatra.dse.api.beestrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.viatra.dse.designspace.api.TrajectoryInfo;
import org.eclipse.viatra.dse.objectives.Fitness;

public class Patch {
	ArrayList<StupidBee> beeList = new ArrayList<StupidBee>();
	//
	TrajectoryInfo patch;
	Fitness bestfitness;
	StupidBee bestBee;
	
	
	public Patch(){
		beeList= new ArrayList<StupidBee>();
		
		 Collections.sort(beeList, new Comparator<StupidBee>() {
			@Override
			public int compare(StupidBee o1, StupidBee o2) {
				System.out.println(o1.toString());
				if(o1.getFitnessInDouble()<o2.getFitnessInDouble())
					return 1;
				return 0;
			}					
		        });
	}	
	
	//getters and setters 
	public ArrayList<StupidBee> getBeeList() {
		if(beeList!=null){
			return beeList;
		}
		return null;
	}
	public void setBeeList(ArrayList<StupidBee> beeList) {
		this.beeList = beeList;
	}
	
	
	public TrajectoryInfo getPatch() {
		return patch;
	}
	public void setPatch(TrajectoryInfo patch) {
		this.patch = patch;
	}
	
	
	public StupidBee getBestBee() {
		if(beeList==null)return null;
		StupidBee bestbee = null;
		Double bestvalue = 0.0;
		for (StupidBee stupidBee : beeList) {	
			if(bestvalue<stupidBee.getFitnessInDouble()){
				bestbee=stupidBee;
				bestvalue=stupidBee.getFitnessInDouble();
			}
		}
		this.bestBee=bestbee;
		return bestbee;
		
	}
	
	public Fitness getBestfitness() {
		return bestfitness;
	}
	public void setBestfitness(Fitness bestfitness) {
		this.bestfitness = bestfitness;
	}
	
	public Double getBestfitnessValue() {
		return bestBee.getFitnessInDouble();
	}
	
	
	
	
}
