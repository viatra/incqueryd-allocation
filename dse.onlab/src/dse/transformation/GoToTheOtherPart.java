package dse.transformation;

import onlab.Land;
import onlab.Vehichle;

import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.viatra.dse.api.DSETransformationRule;

import constraints.SwitchLandMatch;
import constraints.SwitchLandMatcher;
import constraints.util.SwitchLandProcessor;
import constraints.util.SwitchLandQuerySpecification;

public class GoToTheOtherPart {
	public DSETransformationRule<SwitchLandMatch, SwitchLandMatcher>  getinto() throws IncQueryException{
		SwitchLandProcessor ap = new SwitchLandProcessor(){			

			@Override
			public void process(Vehichle pV, Land pStart, Land pCel) {
				Land l= pV.getActualLand();
				if (pStart.getName().equals(l.getName())){
					pV.setActualLand(pCel);
				}
				else pV.setActualLand(pStart);
			}
		};
		
		DSETransformationRule<SwitchLandMatch, SwitchLandMatcher> tr = new DSETransformationRule<SwitchLandMatch, SwitchLandMatcher>(SwitchLandQuerySpecification.instance(), ap);
		return tr;
		
	}

}
