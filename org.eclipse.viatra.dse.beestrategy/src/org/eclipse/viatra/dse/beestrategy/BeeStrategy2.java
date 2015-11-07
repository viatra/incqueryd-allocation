package org.eclipse.viatra.dse.beestrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.eclipse.viatra.dse.api.strategy.interfaces.IStrategy;
import org.eclipse.viatra.dse.base.DesignSpaceManager;
import org.eclipse.viatra.dse.base.ThreadContext;
import org.eclipse.viatra.dse.beestrategy.StupidBee.BeeType;
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
	StupidBee BestBee=null;
	ArrayList<Patch> nextgenerations;
	ArrayList<Patch> BestPatches;

	ArrayList<Patch> patches;
	Integer numberOfActiveBees = 0;
	Patch bestPatch;
	Integer Id = 0;
	Integer numberOfFoundTransitions = 0;
	FilterOptions fo = new FilterOptions().nothingIfCut().nothingIfGoal().untraversedOnly();

	// setable variables
	Integer numberOfMaxBees = 1;

	Integer ProblemSize = null;
	Integer Beesnum = 10;
	Integer SitesNum = 6;
	Integer EliteSitesNum = 3;
	Integer PatchSize = 3;
	Integer EliteBeesNum = 2;
	Integer OtherBeesNum = 1;

	@Override
	public void initStrategy(ThreadContext context) {
		this.patches = new ArrayList<Patch>();
		this.context = context;
		this.dsm = context.getDesignSpaceManager();
		this.solutionStore = context.getGlobalContext().getSolutionStore();
		this.context = context;
	}


	@Override
	public void explore() {
		this.exploreown();
	}


	private void exploreown() {
		//TODO a kezdeti állapot jó megoldás-e 
		
		
		// ArrayList<StupidBee> NextGeneration;
		 BestPatches=new ArrayList<Patch>();
		
		for (int i = 0; i < Beesnum; i++) {
			Patch randomBee = this.createRandomBee(PatchSize);
			randomBee.setBestfitness(context.calculateFitness());
			randomBee.setPatch(context.getDesignSpaceManager().getTrajectoryInfo(), context);
			if (randomBee.getBestfitness().isSatisifiesHardObjectives()) {
				solutionStore.newSolution(context);
				return;
			}
			//this.patches.add(randomBee);
			//best.add(randomBee);
		}
		getBestPatches(SitesNum);
		
		
		while (interrupted != true) {
			//nextgenerations = new ArrayList<Patch>();
			// TODO Evaluate Population			
			// lokalis valtozok kisbeûvel!!!!!!!!
			
			
			// BestBee megkeresése
			BestBee = this.getBestBee(BestPatches);
			if(this.BestBee!=null){
		//		System.out.println("trajectory: "+this.BestBee.getPatchState().getFullTransitionIdTrajectory());
			}
			else System.out.println("hali");
			if (interrupted == true) {
				break;
			}
			
			BestPatches = this.getBestPatches(SitesNum);
			
			System.out.println("next run ------------------------");
			

			for (int i = 0; i < BestPatches.size(); i++) {
				Integer RecruitedBeesNum = 0;
				if (i < EliteSitesNum) RecruitedBeesNum = EliteBeesNum;
				else RecruitedBeesNum = OtherBeesNum;
				for (int j = 0; j < RecruitedBeesNum; j++) {
					this.createNeighbourhoodBee(BestPatches.get(i), PatchSize);
				}
				this.selectBestBeeInPatch(BestPatches.get(i));
			}
			Integer remainingBeesNum = numberOfMaxBees - numberOfActiveBees;
			for (int i = 0; i < remainingBeesNum; i++) {
				Patch randomBee = this.createRandomBee(PatchSize);
				this.patches.add(randomBee);
			}
			if (numberOfFoundTransitions == 0) {
				interrupted = true;
			}
		}
	}

	private Patch createRandomBee(Integer patchSize) {
		DesignSpaceManager dsm = context.getDesignSpaceManager();

		while (dsm.getTrajectoryFromRoot().size() != 0) {
			dsm.undoLastTransformation();
		}
		TrajectoryInfo ti = dsm.getTrajectoryInfo().clone();
		while (patchSize > 0) {
			ITransition nextTran = this.selectNextTransition();
			while (nextTran == null && dsm.getTrajectoryFromRoot().size() >= 0) {
				this.stepBack();
				nextTran = this.selectNextTransition();
				ti.stepBack();
			}
			context.getDesignSpaceManager().fireActivation(nextTran);
			patchSize--;
			ti.addStep(nextTran);
		}
		Patch p = new Patch();
		if (ti.getTransitionTrajectory().size() != 0)
			p.setPatch(ti, context);
		p.setBestfitness(context.calculateFitness());
		this.patches.add(p);
		this.numberOfActiveBees++;
		return p;
	}

	private void selectBestBeeInPatch(Patch patch) {
		for (int i=0; i< patch.getBeeList().size(); i++){
			if(patch.getBestBee().getTrajectoryFitness().rank < patch.getBeeList().get(i).getTrajectoryFitness().rank){
				patch.bestBee = patch.getBeeList().get(i); 
				
			}
		}
		patch.patch=patch.bestBee.actualState;
		//System.out.println(patch.getBestBee());
		this.numberOfActiveBees -= patch.getBeeList().size();
		patch.setBeeList(new ArrayList<StupidBee>());

	}

	private void createNeighbourhoodBee(Patch patch, Integer patchSize) {
		boolean start=true;
		Integer deepness = 0;
		TrajectoryInfo actualState = patch.getPatch().clone();
		this.setThreadContextTo(patch.getPatch());

		// step patchsize many steps
		for (int i = 0; i < patchSize; i++) {
			ITransition nextTran = this.selectNextTransition();
			while (nextTran == null && deepness >= 0) {
				dsm.undoLastTransformation();
				actualState.stepBack();
				deepness--;
				nextTran = this.selectNextTransition();
			}
			if (deepness == 0 && start==false) {
				return;
			}
			start=false;
			context.getDesignSpaceManager().fireActivation(nextTran);
			deepness++;
			actualState.addStep(nextTran);
		}
		StupidBee sb = new StupidBee(Id++, actualState);
		sb.init(Id, actualState, BeeType.Neighbour, context.calculateFitness());
		TrajectoryFitness tf = new TrajectoryFitness(actualState, context.getLastFitness());
		sb.setTrajectoryFitness(tf);
		patch.getBeeList().add(sb);
		this.numberOfActiveBees++;
	}

	private Boolean stepBack() {
		boolean didUndo = dsm.undoLastTransformation();
		// TODO visszalep=> torli az elt
		if (!didUndo) {
			return false;
		}
		return true;
	}

	private ITransition selectNextTransition() {

		// if there is a state from here, which were not processed
		Integer actTranNum = dsm.getTransitionsFromCurrentState(fo).size();
		if (actTranNum > 0) {
			this.numberOfFoundTransitions += actTranNum;
			Collection<? extends ITransition> transitions = dsm.getTransitionsFromCurrentState(fo);
			int index = new Random().nextInt(transitions.size());
			Iterator<? extends ITransition> iterator = transitions.iterator();
			while (iterator.hasNext() && index != 0) {
				index--;
				iterator.next();
			}
			// give back the selected transition
			ITransition transition = iterator.next();
			return transition;
		}

		return null;
	}

	private void setThreadContextTo(TrajectoryInfo patch) {
		DesignSpaceManager dsm = context.getDesignSpaceManager();
		while (dsm.getTrajectoryFromRoot().size() != 0) {
			dsm.undoLastTransformation();
		}
		List<ITransition> transitions = patch.getFullTransitionTrajectory();
		for (ITransition iTransition : transitions) {
			dsm.fireActivation(iTransition);
		}

	}

	private ArrayList<Patch> getBestPatches(Integer bestsize) {
		context.getObjectiveComparatorHelper().clearTrajectoryFitnesses();
		this.BestPatches = new ArrayList<Patch>();
		for (Patch patch : patches) {
			TrajectoryFitness tf = new TrajectoryFitness(patch.getBestBee().getActualState(),
					patch.getBestBee().getFitness());
			//System.out.println(tf);
			patch.getBestBee().setTrajectoryFitness(tf);
			context.getObjectiveComparatorHelper().addTrajectoryFitness(tf);
		}
		
		List<ArrayList<TrajectoryFitness>> fitnessFronts = context.getObjectiveComparatorHelper().getFronts();
		
		//go throw all trajectories, and get the front, and till the size of the front is smaller than the given number we add a new patch to them
		for (ArrayList<TrajectoryFitness> arrayList : fitnessFronts) {
			for (TrajectoryFitness trajectoryFitness : arrayList) {
				for (Patch p : patches) {
					if(p.getBestBee().trajectoryFitness.equals(trajectoryFitness)){
						this.BestPatches.add(p);
					}
					//System.out.println(p.getBestBee().getTrajectoryFitness());
					if (BestPatches.size() >= bestsize)
						break;
				}
				if (BestPatches.size() >= bestsize)
					break;
			}
			if (BestPatches.size() >= bestsize)
				break;
		}
	
		return BestPatches;
	}

	@Override
	public void interruptStrategy() {
		interrupted = true;

	}

	// helper methods

	public StupidBee getBestBee(ArrayList<Patch> bestPatches) {
		if(bestPatches == null || bestPatches.size()==0){
			return null;
		}
		
		if (this.patches == null || this.patches.size() == 0)
			return null;
		return bestPatches.get(0).getBestBee();
	}

	// Generated getters and setters to setable variables

	public Integer getNumberOfMaxBees() {
		return numberOfMaxBees;
	}

	public void setNumberOfMaxBees(Integer numberOfMaxBees) {
		this.numberOfMaxBees = numberOfMaxBees;
	}

	public Integer getProblemSize() {
		return ProblemSize;
	}

	public void setProblemSize(Integer problemSize) {
		ProblemSize = problemSize;
	}

	public Integer getBeesnum() {
		return Beesnum;
	}

	public void setBeesnum(Integer beesnum) {
		Beesnum = beesnum;
	}

	public Integer getSitesNum() {
		return SitesNum;
	}

	public void setSitesNum(Integer sitesNum) {
		SitesNum = sitesNum;
	}

	public Integer getEliteSitesNum() {
		return EliteSitesNum;
	}

	public void setEliteSitesNum(Integer eliteSitesNum) {
		EliteSitesNum = eliteSitesNum;
	}

	public Integer getPatchSize() {
		return PatchSize;
	}

	public void setPatchSize(Integer patchSize) {
		PatchSize = patchSize;
	}

	public Integer getEliteBeesNum() {
		return EliteBeesNum;
	}

	public void setEliteBeesNum(Integer eliteBeesNum) {
		EliteBeesNum = eliteBeesNum;
	}

	public Integer getOtherBeesNum() {
		return OtherBeesNum;
	}

	public void setOtherBeesNum(Integer otherBeesNum) {
		OtherBeesNum = otherBeesNum;
	}
}
