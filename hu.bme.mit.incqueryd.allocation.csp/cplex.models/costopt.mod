/*********************************************
 * OPL 12.6.1.0 Model
 * Author: Jozsef
 * Creation Date: Feb 26, 2015 at 11:22:27 PM
 *********************************************/
 
using CP;

int n = ...;
int processes[1..n] = ...;
int m = ...;
int capacities[1..m] = ...;
int costs[1..m] = ...;

dvar int x[1..m][1..n] in 0..1;
dvar int w[1..m];

int sum_cost = sum(i in 1..m) costs[i];

dexpr int sum_W = sum(i in 1..m) w[i];

execute {

	var f = cp.factory;
	
	var phase_X = f.searchPhase(x);
	var phase_W = f.searchPhase(w);
	
	// x alapján kezdjük el a vágást!
	cp.setSearchPhases(phase_X, phase_W);
	
	cp.param.timeLimit=5;
}

minimize sum_W;

subject to {

	forall (i in 1..m) w[i] in {0, costs[i]};

	sum_W <= sum_cost;
	sum_W >= 0;

	forall (j in 1..n) sum(i in 1..m) x[i][j] == 1;
	
	forall (i in 1..m) sum(j in 1..n) processes[j]*x[i][j] <= capacities[i];
	
	forall (i in 1..m) (sum (j in 1..n) x[i][j] >= 1) => (w[i] == costs[i]);
	forall (i in 1..m) (sum (j in 1..n) x[i][j] < 1) => (w[i] == 0);
}