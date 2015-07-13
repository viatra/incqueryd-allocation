package dse.onlab;

import java.util.Collection;

import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.viatra.dse.api.DSETransformationRule;
import org.eclipse.viatra.dse.api.DesignSpaceExplorer;
import org.eclipse.viatra.dse.api.Solution;
import org.eclipse.viatra.dse.api.Strategies;
//import org.eclipse.viatra.dse.api.beestrategy.BeeStrategy2;
import org.eclipse.viatra.dse.api.strategy.impl.FixedPriorityStrategy;
import org.eclipse.viatra.dse.objectives.impl.ModelQueriesGlobalConstraint;
import org.eclipse.viatra.dse.objectives.impl.ModelQueriesHardObjective;
import org.eclipse.viatra.dse.solutionstore.SimpleSolutionStore;

import constraints.util.DangerousPassangersAtOnePlaceQuerySpecification;
import constraints.util.NullPassangerAtWrongPlaceQuerySpecification;
import dse.problems.StartProblem;
import dse.transformation.GetIntoBoat;
import dse.transformation.GetOutOfBoat;
import dse.transformation.GoToTheOtherPart;

public class setUp {
	 protected DesignSpaceExplorer dse;
	 protected onlab.PassTheRiver model;
	 //dseDSETransformationRule
	 protected DSETransformationRule<?, ?> getInTheVehichle;
	 protected DSETransformationRule<?, ?> getOutOfVehichle;
	 protected DSETransformationRule<?, ?> switchLands;
	 FixedPriorityStrategy fps;
	 //BeeStrategy2 bs;
	 
	public void setUpProject() throws IncQueryException{
		
		 dse = new DesignSpaceExplorer();
		 model = new StartProblem().getpt().getPassTheRiver();
		 System.out.println("model"+model.toString());
		 dse.setInitialModel(model);
		 
		 this.getInTheVehichle = new GetIntoBoat().getinto();
		 this.getOutOfVehichle = new GetOutOfBoat().getinto();
		 this.switchLands = new GoToTheOtherPart().getinto();
		
		 
		 //set constraints
		 System.out.println("hali");
		 dse.addObjective(new ModelQueriesHardObjective("MyHardObjective")
		    .withConstraint(NullPassangerAtWrongPlaceQuerySpecification.instance()));
		 System.out.println("hali2");
		 dse.setStateCoderFactory(new OwnSerializerFactory());
		 System.out.println("hali3");
		 dse.addTransformationRule(this.getInTheVehichle);
		 dse.addTransformationRule(this.getOutOfVehichle);
		 dse.addTransformationRule(this.switchLands);
		 System.out.println("hali4");
		 dse.addGlobalConstraint(new ModelQueriesGlobalConstraint()
		  .withConstraint(DangerousPassangersAtOnePlaceQuerySpecification.instance()));
		 System.out.println("hali5");
		 /*	 dse.addGlobalConstraint(new IGlobalConstraint() {
			
			@Override
			public void init(ThreadContext context) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public IGlobalConstraint createNew() {
				// TODO Auto-generated method stub
				return this;
			}
			
			@Override
			public boolean checkGlobalConstraint(ThreadContext context) {
				List<ITransition> iti = context.getDesignSpaceManager().getTrajectoryInfo().getFullTransitionTrajectory();
				int size = iti.size();
				if (size<2){
					return true;
				}
				ITransition last = iti.get(size-1);
				if (size<=10){
					for (int i=0; i<size-1; i++){
						if (iti.get(i).getId().equals(last.getId())){
							return false;
						}
					}
				}	
				else{
					for (int i=size-10; i<size-1; i++){
						if (iti.get(i).getId().equals(last.getId())){
							return false;
						}
					}
				}
				return true;
				
			}
		});*/
		 dse.setMaxNumberOfThreads(1);
		 dse.setSolutionStore(new SimpleSolutionStore(1));
		 System.out.println("hali");
	//	 bs = new BeeStrategy2();

		  fps = new FixedPriorityStrategy()
         .withRulePriority(this.getInTheVehichle, 4)
         .withRulePriority(this.getOutOfVehichle, 4)
         .withRulePriority(this.switchLands, 4);
		 System.out.println("hali");
		 

	}
	public void startProject(){
		//bs.explore();
		//dse.startExploration(bs);
		System.out.println("HALI");
		//dse.startExploration(Strategies.createDFSStrategy(16));
		dse.startExploration(fps);
		System.out.println("most");
		//dse.startExploration(bs);
	}
	
	public void writeOutProject() throws IncQueryException{
		 Collection<Solution> solutions = dse.getSolutions();
		 System.out.println(solutions.size());
	}
}
