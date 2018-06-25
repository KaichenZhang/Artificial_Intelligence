package demo2;
import java.util.*;

//BFS search for eight puzzle
public class BFSEP {
	public static void search(List<String> input){
		//set timer
		long startTime = System.currentTimeMillis();	
		int[] board = cast(input);
		
		State root = new State(new EightPuzzle(board));
		
		//using sum of inversions to check whether the puzzle is solvable
		if((root.getCurState().getSOI()%2) == 0){		
		//by using queue we can do such operation: 
		//remove leftmost element from open list,add successors to the right most of open list
		    Queue<State> queue = new LinkedList<State>();
		    queue.add(root);
		    search(queue);
		    long endTime = System.currentTimeMillis();
		    long totalTime = endTime - startTime;
		    System.out.println("Running time is:\n" + totalTime + "milliseconds");
		}
		else{
			System.out.println("Sorry,the puzzle is not solvable...");
		}
	}
	
	//implement the search
	public static void search(Queue<State> q){		
		while (!q.isEmpty()){
			//current position pointer,take the head of the queue
			State curState = (State) q.poll();
			
			if (!curState.getCurState().isGoal()){
				ArrayList<EightPuzzle> candidates = curState.getCurState().getSuccessors(); 
				//check all the successors,if it's never been visited add it to the open list
				for (int i = 0; i < candidates.size(); i++){
					State t = new State(curState,candidates.get(i), curState.getACost() + candidates.get(i).costs(), 0);
					if (!visit(t))
						//add successors to the right most of open list
						q.add(t);
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
				
				//push the root,AKA the goal
				solutionStack.push(curState);
				int size = solutionStack.size();
				
				//to print the result
				for (int i = 0; i < size; i++){
					curState = solutionStack.pop();
					curState.getCurState().printBoards();
					System.out.println("\n");
				}

				break;
			}
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

		
	//check if the current state has ever been reached
	private static boolean visit(State s){
		State curState = s;
		boolean visited = false;
		
		while ((s.getParent() != null) && (!visited)){
			if ((s.getParent().getCurState()) == (curState.getCurState())){
				visited = true;
				}
			s = s.getParent();
			}
		return visited;
	}
}
