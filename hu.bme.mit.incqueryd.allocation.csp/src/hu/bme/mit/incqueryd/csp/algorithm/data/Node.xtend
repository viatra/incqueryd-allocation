package hu.bme.mit.incqueryd.csp.algorithm.data

@Data
class Node {
	int id
	String name
	int size
	
	new(){
		_id = -1
		_name = ""
		_size = 0
	}
	
	new(int id, String name, int size){
		_id = id
		_name = name
		_size = size
	}
}