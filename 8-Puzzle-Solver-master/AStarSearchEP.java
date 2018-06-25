package demo2;
import java.util.*;

//A* search for eight puzzle
public class AStarSearchEP{
	public static void search(List<String> input, int heuristic){
		//set timer
		long startTime = System.currentTimeMillis();
		int[] board = cast(input);
		State root = new State(new EightPuzzle(board));
		//using sum of inversions to check whether the puzzle is solvable
		if((root.getCurState().getSOI()%2) == 0){
			Queue<State> queue = new LinkedList<State>();
			queue.add(root);
			
			while (!queue.isEmpty()){
				//current position pointer,take the head of the queue
				State curState = (State) queue.poll();
				
				if (!curState.getCurState().isGoal()){
					//to get all successors to current state
					ArrayList<EightPuzzle> candidates = curState.getCurState().getSuccessors();
					//to store certified successors
					ArrayList<State> successors = new ArrayList<State>();
					
					for (int i = 0; i < candidates.size(); i++){
						State temp = null;
						//check the heuristic to be used from the input,construct new state 
						if (heuristic == 0){
							temp = new State(curState,candidates.get(i), curState.getACost()+ candidates.get(i).costs(),(candidates.get(i)).getMisplacedTiles());
							
						}
						if (heuristic == 1){
							temp = new State(curState,candidates.get(i), curState.getACost()+ candidates.get(i).costs(),(candidates.get(i)).getManDist());
							
						}
						if (heuristic == 2){
							temp = new State(curState,candidates.get(i), curState.getACost()+ candidates.get(i).costs(),Math.min((candidates.get(i)).getMisplacedTiles(),(candidates.get(i)).getManDist()));
							
						}
						//admissible heuristic Straight Line Distance
						if (heuristic == 3){
							temp = new State(curState,candidates.get(i), curState.getACost()+ candidates.get(i).costs(),(candidates.get(i)).getAdmissibleDist());
							
						}
						
						//check all the successors,if it's never been visited add it to the open list
						if (!visit(temp)){
							successors.add(temp);
							
						}
						
					}
					
					if (successors.size() != 0){
						State leftMost = successors.get(0);
						
						//to find the state with the lowest f(n)
						//to make sure it's admissible
						for (int i = 0; i < successors.size(); i++){
							if (leftMost.getFCost() > successors.get(i).getFCost()){
								leftMost = successors.get(i);
								
							}
							
						}
						
						int lowestFCost = (int) leftMost.getFCost();
						
						// to add all states with lowest f(n) to the successors
						for (int i = 0; i < successors.size(); i++){
							if (successors.get(i).getFCost() == lowestFCost){
								queue.add(successors.get(i));
								
							}
							
						}
						
					}
					
				}
				
				//solution has been found, using a stack to track back the steps
				else{
					Stack<State> solutionStack = new Stack<State>();
					solutionStack.push(curState);
					curState = curState.getParent();
					
					while (curState.getParent() != null){
						solutionStack.push(curState);
						curState = curState.getParent();
					
					}
					
					solutionStack.push(curState);
					int size = solutionStack.size();
					
					for (int i = 0; i < size; i++){
						curState = solutionStack.pop();
						curState.getCurState().printBoards();
						System.out.println("\n");
						
					}
					
					long endTime = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					System.out.println("Running time is:\n" + totalTime + "milliseconds");
					break;
					
				}
				
			}
			
		}
		
		else{
		System.out.println("Sorry,the puzzle is not solvable...");
		
	}
	
}
	
	// Helper method to build our initial 8puzzle state passed in through app
	private static int[] cast(List<String> list){
		int[] initState = new int[9];
		// i -> loop counter
		for (int i = 0; i < list.size(); i++){
			if(list.get(i) != "B"){
				initState[i] = Integer.parseInt(list.get(i));				
				
			}	
			else
				initState[i] = 0;	
			
		}
		return initState;
		
	}

	private static boolean visit(State s){
		boolean visited = false;
		State curState = s;

		// While n's parent isn't null, check to see if it's equal to the node
		// we're looking for.
		while (s.getParent() != null && !visited)
		{
			if (s.getParent().getCurState().equals(curState.getCurState()))
			{
				visited = true;
			}
			s = s.getParent();
		}

		return visited;
	}

}
