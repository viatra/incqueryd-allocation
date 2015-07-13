package dse.onlab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import onlab.Land;
import onlab.PassTheRiver;
import onlab.Passanger;
import onlab.Vehichle;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.viatra.dse.api.DSEException;
import org.eclipse.viatra.dse.statecode.IStateCoder;

import constraints.GetIntoMatch;
import constraints.GetOutMatch;
import constraints.SwitchLandMatch;

public class Serializer implements IStateCoder{
	 private PassTheRiver model;
	 private ArrayList<onlab.Land> land;
	 private ArrayList<Vehichle> vehichles;
	 private ArrayList<Passanger> passangers;


	@Override
	public void init(Notifier modelRoot) {
		this.model=(PassTheRiver)modelRoot;	
		 land = new ArrayList<onlab.Land>();
		 for (onlab.Land lands : model.getLands()){
			 land.add(lands);
		 }
		 
		 Collections.sort(land, new Comparator<onlab.Land>() {

			@Override
			public int compare(Land o1, Land o2) {
				return o1.getName().compareTo(o2.getName());
			}

				
	        });
		 passangers = new ArrayList<onlab.Passanger>();
		 vehichles = new ArrayList<Vehichle>();
		 for (Passanger p : model.getPassangers()){
			 passangers.add(p);
		 }
		 Collections.sort(passangers, new Comparator<Passanger>() {

				@Override
				public int compare(Passanger arg0, Passanger arg1) {
					
					return arg0.getName().compareTo(arg1.getName());
				}
	        });
		 
		 vehichles= new ArrayList<Vehichle>();
		 for (Vehichle v : model.getVehichles()){
			 vehichles.add(v);
		 }
		 
		 Collections.sort(vehichles, new Comparator<Vehichle>() {

			@Override
			public int compare(Vehichle v1, Vehichle v2) {
				return v1.getName().compareTo(v2.getName());
			}

				
	        });
	}

	@Override
	public Object createStateCode() {
		Boolean h = false;
		StringBuilder sb = new StringBuilder();
		   for (onlab.Land o : land) {
			   if(o.getName().equals("start") && o.getPassangers().size()==0) h = true;
	            sb.append(o.getName());
	            sb.append(":");
	            for (int i=0; i<this.passangers.size(); i++){
	            	if (o.getPassangers().contains(passangers.get(i))){
	            		sb.append(passangers.get(i).getName());
	            		sb.append(",");
	            	}
	            }
	            sb.append("  ");
	        }
		   for (Vehichle v : vehichles) {
	            sb.append(v.getName());
	            sb.append(":");
	            for (int i=0; i<this.passangers.size(); i++){
	            	if(passangers.get(i).getTravelOn()!=null){
		            	if (v.getName().equals(passangers.get(i).getTravelOn().getName())){
		            		sb.append(passangers.get(i).getName());
		            		sb.append(",");
		            	}
	            	}
	            }
	            sb.append("  ");
	        }
		   
		   if(h==true) 
	     System.out.println(sb);
		return sb;
	}





	@Override
	public Object createActivationCode(IPatternMatch match) {
		if (match instanceof GetIntoMatch) {
			GetIntoMatch m = (GetIntoMatch) match;
			//System.out.println(m.getP().getName()+": "+m.getV().getActualLand().getName()+"->"+m.getV().getName());
			return m.getP().getName()+": "+m.getV().getActualLand().getName()+"->"+m.getV().getName();
        } else if (match instanceof GetOutMatch) {
        	GetOutMatch m = (GetOutMatch) match;
        	//System.out.println(m.getP().getName()+": "+m.getV().getName()+"->"+m.getV().getActualLand().getName());
           return m.getP().getName()+": "+m.getV().getName()+"->"+m.getV().getActualLand().getName();
        } else if (match instanceof SwitchLandMatch) {
        	SwitchLandMatch m = (SwitchLandMatch) match;
        	//System.out.println(m.getV().getName()+": "+m.getStart().getName()+"->"+m.getCel().getName());
           return m.getV().getName()+": "+m.getStart().getName()+"->"+m.getCel().getName();
       } else {
            throw new DSEException("Unsupported rule.");
        }
	}
	

}

