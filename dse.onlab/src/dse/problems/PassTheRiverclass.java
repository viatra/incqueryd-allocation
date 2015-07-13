package dse.problems;

import onlab.*;



public class PassTheRiverclass {
	OnlabFactory factory;
	PassTheRiver passtr;
	
	public PassTheRiver getPassTheRiver(){
		return passtr;
	}
	public PassTheRiverclass(PassTheRiver passtr){
		factory = OnlabFactory.eINSTANCE;
		this.passtr=passtr;
	}
	
	public PassTheRiverclass(){
		factory = OnlabFactory.eINSTANCE;
		this.passtr=factory.createPassTheRiver();
	}
	
	public Danger createDanger(){
		Danger d = factory.createDanger();
		passtr.getDanger().add(d);
		return d;
	}
	
	public Driver createDriver(){
		Driver d = factory.createDriver();
		passtr.getPassangers().add(d);
		return d;
	}
	
	public Land createLand(){
		Land d = factory.createLand();
		passtr.getLands().add(d);
		return d;
	}
	
	public Passanger createPassanger(){
		Passanger d = factory.createPassanger();
		passtr.getPassangers().add(d);
		return d;
	}
	
	public Vehichle createVehichles(){
		Vehichle d = factory.createVehichle();
		passtr.getVehichles().add(d);
		return d;
	}
	
	
}
