package dse.problems;


public class StartProblem {
	PassTheRiverclass pt;
	/* public StartProblem (){
		 	pt= new PassTheRiverclass();
		 	onlab.Land startPoint =pt.createLand();
			startPoint.setName("start");
			onlab.Land targetPoint =pt.createLand();
			targetPoint.setName("target");
			startPoint.getNeighbours().add(targetPoint);
			targetPoint.getNeighbours().add(startPoint);
			
			//create animals and driver
			onlab.Passanger wolf = pt.createPassanger();	
			wolf.setName("wolf");
			onlab.Passanger goat = pt.createPassanger();	
			goat.setName("goat");
			onlab.Passanger cabbage = pt.createPassanger();		
			cabbage.setName("cabbage");
			onlab.Driver man = pt.createDriver();
			man.setName("man");
					
			//target for everybody
			wolf.setTarget(targetPoint);
			goat.setTarget(targetPoint);
			cabbage.setTarget(targetPoint);
			man.setTarget(targetPoint);
			
			//startpoint for everybody 
			wolf.setLand(startPoint);
			goat.setLand(startPoint);
			cabbage.setLand(startPoint);
			man.setLand(startPoint);
			
			//create the boat
			onlab.Vehichle boat = pt.createVehichles();
			
			//add the boat to the lands
			boat.getLands().add(startPoint);
			boat.getLands().add(targetPoint);
			boat.setActualLand(startPoint);
			boat.setName("boat");
			boat.setSeats(2);
			//add the man to the boat
			man.getDrivingLicences().add(boat);
			
			//create Dangers and set them
			
			onlab.Danger wolfToGoat = pt.createDanger();
			wolfToGoat.getDangerousToEachOther().add(wolf);
			wolfToGoat.getDangerousToEachOther().add(goat);
			wolfToGoat.getGuard().add(man);
			
			/*onlab.Danger goatToCabbage = pt.createDanger();
			goatToCabbage.getDangerousToEachOther().add(cabbage);
			goatToCabbage.getDangerousToEachOther().add(goat);
			goatToCabbage.getGuard().add(man);
			
			//add passangers to lands
			startPoint.getPassangers().add(cabbage);
			startPoint.getPassangers().add(goat);
			startPoint.getPassangers().add(wolf);
			startPoint.getPassangers().add(man);
		
			
			
	 }*/
	 
	 public StartProblem(){
		pt= new PassTheRiverclass();
	 	onlab.Land startPoint =pt.createLand();
		startPoint.setName("start");
		onlab.Land targetPoint =pt.createLand();
		targetPoint.setName("target");
		startPoint.getNeighbours().add(targetPoint);
		targetPoint.getNeighbours().add(startPoint);
		
		onlab.Driver man = pt.createDriver();
		man.setName("man");
		
		onlab.Passanger cabbage = pt.createPassanger();		
		cabbage.setName("cabbage");
		
		cabbage.setLand(startPoint);
		man.setLand(startPoint);
		
		cabbage.setTarget(targetPoint);
		man.setTarget(targetPoint);
		

		//create the boat
		onlab.Vehichle boat = pt.createVehichles();
		
		//add the boat to the lands
		boat.getLands().add(startPoint);
		boat.getLands().add(targetPoint);
		boat.setActualLand(startPoint);
		boat.setName("boat");
		boat.setSeats(2);
		//add the man to the boat
		man.getDrivingLicences().add(boat);
		
		startPoint.getPassangers().add(man);
		startPoint.getPassangers().add(cabbage);
	 }
	 
	 public PassTheRiverclass getpt(){
		 return this.pt;
	 }

}
