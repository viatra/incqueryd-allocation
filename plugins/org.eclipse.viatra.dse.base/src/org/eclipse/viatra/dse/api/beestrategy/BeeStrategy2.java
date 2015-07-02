package org.eclipse.viatra.dse.api.beestrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.eclipse.viatra.dse.api.beestrategy.StupidBee.BeeType;
import org.eclipse.viatra.dse.api.strategy.interfaces.IStrategy;
import org.eclipse.viatra.dse.base.DesignSpaceManager;
import org.eclipse.viatra.dse.base.ThreadContext;
import org.eclipse.viatra.dse.designspace.api.IGetCertainTransitions.FilterOptions;
import org.eclipse.viatra.dse.designspace.api.ITransition;
import org.eclipse.viatra.dse.designspace.api.TrajectoryInfo;
import org.eclipse.viatra.dse.objectives.Fitness;
import org.eclipse.viatra.dse.objectives.TrajectoryFitness;
import org.eclipse.viatra.dse.solutionstore.ISolutionStore;

public class BeeStrategy2 implements IStrategy {
	
	ThreadContext context;
	DesignSpaceManager dsm;
	Boolean interrupted = false;
	ISolutionStore solutionStore;
	
	HashMap<Fitness, Patch> patches;
	Integer numberOfMaxBees=1;
	Integer numberOfActiveBees=0;
	Patch bestPatch;
	Integer Id = 0;
	
	
	@Override
	public void initStrategy(ThreadContext context) {
		this.patches = new HashMap<Fitness, Patch>();
		this.context = context;
		this.dsm = context.getDesignSpaceManager();
		this.solutionStore = context.getGlobalContext().getSolutionStore();	
		this.context = context;
	}
	
	public void initStrategy(ThreadContext context, Integer maxBees) {
		this.patches = new HashMap<Fitness, Patch>();
		this.context = context;
		this.dsm = context.getDesignSpaceManager();
		this.solutionStore = context.getGlobalContext().getSolutionStore();		
		this.numberOfMaxBees=maxBees;
	}
	
	
	@Override
	public void explore() {
		//TODO how to get sizes
		this.exploreown(10, 10, 3, 1, 6, 2, 1);
	}
	
	private void exploreown(Integer ProblemSize, Integer Beesnum, Integer SitesNum, Integer EliteSitesNum, Integer PatchSize, Integer EliteBeesNum, Integer OtherBeesNum){
		StupidBee BestBee;
		//ArrayList<StupidBee> NextGeneration;
		ArrayList<Patch> BestPatches;	
		for (int i=0; i<SitesNum; i++){
			Patch randomBee = this.createRandomBee(PatchSize);
			this.patches.put(randomBee.getBestfitness(),randomBee);
		}
		while(interrupted!=true){
			// TODO Evaluate Population 
			//BestBee
			BestBee = this.getBestBee();
			//NextGeneration = new ArrayList<StupidBee>();
			BestPatches= this.getBestPatches(SitesNum);			
			
			for(int i=0; i<BestPatches.size(); i++){
				Integer RecruitedBeesNum=0;				
				if(i<EliteSitesNum)
					RecruitedBeesNum = EliteBeesNum;
				RecruitedBeesNum = OtherBeesNum;
				for(int j=0; j<RecruitedBeesNum; j++){
					this.createNeighbourhoodBee(BestPatches.get(i), PatchSize);
				}
				this.selectBestBeeInPatch(BestPatches.get(i));
			}
			Integer remainingBeesNum = numberOfMaxBees-numberOfActiveBees;
			for (int i=0; i<remainingBeesNum; i++){
				Patch randomBee = this.createRandomBee(PatchSize);
				this.patches.put(randomBee.getBestfitness(),randomBee);
			}
		Fitness maxFitness = null;
		if(BestBee.getFitness()==maxFitness){
			this.interruptStrategy();
		}
		}
	}

	private Patch createRandomBee(Integer patchSize) {	
		DesignSpaceManager dsm = context.getDesignSpaceManager();
		
		while(dsm.getTrajectoryFromRoot().size()!=0){
			dsm.undoLastTransformation();
		}
		TrajectoryInfo ti = dsm.getTrajectoryInfo();
		while(patchSize>0){
			ITransition nextTran = this.selectNextTransition();
			while(nextTran == null && dsm.getTrajectoryFromRoot().size()>=0){
				this.stepBack();
				nextTran = this.selectNextTransition();
			}
			context.getDesignSpaceManager().fireActivation(nextTran);
			patchSize++;
			ti.addStep(nextTran);
		}
		Patch p = new Patch();
		p.setPatch(ti);
		p.setBestfitness(context.calculateFitness());
		this.patches.put(p.getBestfitness(), p);
		this.numberOfActiveBees++;
		return p;
	}

	private void selectBestBeeInPatch(Patch patch) {
		//TODO
		patch.getBestBee();
		this.numberOfActiveBees-=patch.getBeeList().size();
		patch.setBeeList(null);
		
	}

	private void createNeighbourhoodBee(Patch patch, Integer patchSize) {
		Integer deepness = 0; 
		TrajectoryInfo actualState=patch.getPatch().clone();
		this.setThreadContextTo(patch.getPatch());
		
		//step patchsize many steps
		for(int i=0; i< patchSize; i++){
			ITransition nextTran = this.selectNextTransition();
			while(nextTran == null && deepness>=0){
				this.stepBack();
				deepness--;
				nextTran = this.selectNextTransition();
			}
			if(deepness==0){
				return;
			}
			context.getDesignSpaceManager().fireActivation(nextTran);
			deepness++;
			actualState.addStep(nextTran);
		}
		StupidBee sb = new StupidBee(Id++, actualState);
		sb.setBeeType(BeeType.Neighbour);
		patch.getBeeList().add(sb);	
		this.numberOfActiveBees++;
	}

	private Boolean stepBack() {
		boolean didUndo = dsm.undoLastTransformation();
		
		 if (!didUndo) {
               return false;
           }	
		return true;
	}

	private ITransition selectNextTransition() {
		FilterOptions fo =  new FilterOptions().nothingIfCut().nothingIfGoal().untraversedOnly();
		//if there is a state from here, which were not processed
		 if (dsm.getTransitionsFromCurrentState(fo).size() <= 0){			 
			 Collection<? extends ITransition> transitions = dsm.getTransitionsFromCurrentState(fo);
			 int index = new Random().nextInt(transitions.size()); 
		     Iterator<? extends ITransition> iterator = transitions.iterator();
		     while (iterator.hasNext() && index != 0) {
		            index--;
		            iterator.next();
		     }
	        //give back the selected transition
	        ITransition transition = iterator.next();
	        return transition;
		 }
            
		 return null;
	}

	private void setThreadContextTo(TrajectoryInfo patch) {
		DesignSpaceManager dsm = context.getDesignSpaceManager();
		while(dsm.getTrajectoryFromRoot().size()!=0){
			dsm.undoLastTransformation();
		}
		List<ITransition> transitions = patch.getFullTransitionTrajectory();
		for (ITransition iTransition : transitions) {
			dsm.fireActivation(iTransition);
		}
		
	}

	private ArrayList<Patch> getBestPatches(Integer bestsize) {
		ArrayList<TrajectoryFitness> fitnesses = new ArrayList<TrajectoryFitness>();
		for (int i=0; i<this.patches.size();i++){		
			TrajectoryFitness tf = new TrajectoryFitness(patches.get(i).getBestBee().getActualState(), patches.get(i).getBestBee().getFitness());
			fitnesses.add(tf);
		}
		context.getObjectiveComparatorHelper().getFronts().add(fitnesses);
		List<ArrayList<TrajectoryFitness>> fitnessFronts = context.getObjectiveComparatorHelper().getFronts();
		
		ArrayList<Patch> BestPatches = new ArrayList<Patch>();
		
		for (int i=0; i<fitnessFronts.size(); i++){
			for (int j=0; j<fitnessFronts.get(i).size(); j++){
				for (int k=0; k<patches.size(); k++){
					if (patches.get(k).getBestBee().getFitness().equals(fitnessFronts.get(i).get(j).fitness)){
						BestPatches.add(patches.get(k));
						if(BestPatches.size()>=bestsize) break;
					}
				}
				if(BestPatches.size()>=bestsize) break;
			}
			if(BestPatches.size()>=bestsize) break;
		}
		return BestPatches;
	}

	@Override
	public void interruptStrategy() {
		interrupted  = true;
		
	}
	
	
	//helper methods
	
	public StupidBee getBestBee(){
		if(this.patches==null || this.patches.size()==0) return null;
		Double best = 0.0;
		StupidBee actualStupidBee = null;
		for (Entry<Fitness, Patch> patch  : patches.entrySet()) {
			if (patch.getValue().getBestfitnessValue()>best){
				best=patch.getValue().getBestfitnessValue();
				actualStupidBee=patch.getValue().getBestBee();
			}			
		}
		
		return actualStupidBee;
		
	}
}
