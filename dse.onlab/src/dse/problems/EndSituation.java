package dse.problems;

public class EndSituation {
	PassTheRiverclass pt;
	 public EndSituation (){
		 	pt= new PassTheRiverclass();
		 	onlab.Land startPoint =pt.createLand();
			startPoint.setName("start");
			onlab.Land targetPoint =pt.createLand();
			startPoint.setName("target");
			startPoint.getNeighbours().add(targetPoint);
			startPoint.getNeighbours().add(startPoint);
			//create animals and driver
			onlab.Passanger wolf = pt.createPassanger();			
			onlab.Passanger goat = pt.createPassanger();			
			onlab.Passanger cabbage = pt.createPassanger();			
			onlab.Driver man = pt.createDriver();
					
			//target for everybody
			wolf.setTarget(targetPoint);
			goat.setTarget(targetPoint);
			cabbage.setTarget(targetPoint);
			man.setTarget(targetPoint);
			
			//create the boat
			onlab.Vehichle boat = pt.createVehichles();
			
			//add the boat to the lands
			boat.getLands().add(startPoint);
			boat.getLands().add(targetPoint);
			
			//add the man to the boat
			man.getDrivingLicences().add(boat);
			
			//create Dangers and set them
			
			onlab.Danger wolfToGoat = pt.createDanger();
			wolfToGoat.getDangerousToEachOther().add(wolf);
			wolfToGoat.getDangerousToEachOther().add(goat);
			wolfToGoat.getGuard().add(man);
			
			onlab.Danger goatToCabbage = pt.createDanger();
			goatToCabbage.getDangerousToEachOther().add(cabbage);
			goatToCabbage.getDangerousToEachOther().add(goat);
			goatToCabbage.getGuard().add(man);
			
			//add passangers to lands
			targetPoint.getPassangers().add(cabbage);
			targetPoint.getPassangers().add(goat);
			targetPoint.getPassangers().add(wolf);
			targetPoint.getPassangers().add(man);
	 }
	 public PassTheRiverclass getpt(){
		 return this.pt;
	 }
}
