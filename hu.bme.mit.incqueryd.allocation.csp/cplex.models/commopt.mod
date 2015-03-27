/*********************************************
 * OPL 12.6.1.0 Model
 * Author: Jozsef
 * Creation Date: Feb 14, 2015 at 2:03:03 PM
 *********************************************/
 
using CP;

int n = ...;
int processes[1..n] = ...;
int edges[1..n][1..n] = ...;
int m = ...;
int capacities[1..m] = ...;
int overheads[1..m][1..m] = ...;

dvar int x[1..m][1..n] in 0..1;
dvar int w[1..n][1..n];

int max_O = max (i,j in 1..m) overheads[i][j];
int min_O = min (i,j in 1..m) overheads[i][j];

int sum_edges = sum(i,j in 1..n) edges[i][j];

dexpr int sum_W = sum(i,j in 1..n) w[i][j];

execute {

	var f = cp.factory;
	
	var phase_X = f.searchPhase(x);
	var phase_W = f.searchPhase(w);
	
	// x alapján kezdjük el a vágást!
	cp.setSearchPhases(phase_X, phase_W);
	
	cp.param.timeLimit=5;
}

// célfüggvény kivezetése döntési változóba és arra kényszereket írunk fel
minimize sum_W;

subject to {
	forall (i,j in 1..n) w[i][j] <= max_O * edges[i][j];
	forall (i,j in 1..n) w[i][j] >= min_O * edges[i][j];
	
	sum_W <= sum_edges * max_O;
	sum_W >= sum_edges * min_O;

	forall (j in 1..n) sum(i in 1..m) x[i][j] == 1;
	
	forall (i in 1..m) sum(j in 1..n) processes[j]*x[i][j] <= capacities[i];
	
	forall (i,j in 1..m) forall (k,l in 1..n) (x[i][k]+x[j][l] >= 2) => w[k][l] == edges[k][l]*overheads[i][j];
}