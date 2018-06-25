package demo2;

//using class State to encapsulate eight puzzle state, provide constructors for each search type
public class State{
	private State parent;
	private EightPuzzle curState;
	private int aCost; // cost to get to this state
	private int hCost; // heuristic cost
	private int fCost; // f(n) cost
	private double sldhCost;// heuristic cost for Straight Line Distance

	//initial constructor
	public State(EightPuzzle s){
		curState = s;
		parent = null;
		aCost = 0;
		hCost = 0;
		fCost = 0;
	}
	
	//constructor for leaves of greedy search
	public State(State a, EightPuzzle s, int h){
		parent = a;
		curState = s;
		//choose the number of misplaced tiles as heuristic
		hCost = h;
	}

	//constructor for leaves of A* search
	public State(State a, EightPuzzle s, int c, int h){
		parent = a;
		curState = s;
		aCost = c;
		hCost = h;
		fCost = aCost + hCost;
	}
	
	//constructor for Straight Line Distance
	public State(State a, EightPuzzle s, int c, double h){
		parent = a;
		curState = s;
		aCost = c;
		sldhCost = h;
		fCost = aCost + hCost;
	}

	//get current state
	public EightPuzzle getCurState(){
		return curState;
	}

	//get parent state
	public State getParent(){
		return parent;
	}
	
	//get actual cost from start to current state
	public int getACost(){
		return aCost;
	}
	
	//get heuristic cost 
	public int getHCost(){
		return hCost;
	}
	
	//get heuristic cost 
	public double getSLDHCost(){
		return sldhCost;
	}
	
	//get f(n)
	public int getFCost(){
		return fCost;
	}
}
