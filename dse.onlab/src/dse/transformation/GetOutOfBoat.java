package dse.transformation;

import onlab.Passanger;
import onlab.Vehichle;

import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.viatra.dse.api.DSETransformationRule;

import constraints.*;
import constraints.util.*;


public class GetOutOfBoat {
	
	public DSETransformationRule<GetOutMatch, GetOutMatcher> getinto() throws IncQueryException{
		GetOutProcessor ap = new GetOutProcessor(){			


			@Override
			public void process(Vehichle pV, Passanger pP) {
				pV.getActualLand().getPassangers().add(pP);
				pP.setTravelOn(null);
				pP.setLand(pV.getActualLand());
			}
		};
		
		DSETransformationRule<GetOutMatch, GetOutMatcher>  tr = new DSETransformationRule<GetOutMatch, GetOutMatcher> (GetOutQuerySpecification.instance(), ap);
		return tr;
		
	}

}
