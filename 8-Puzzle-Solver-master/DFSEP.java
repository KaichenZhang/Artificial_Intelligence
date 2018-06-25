package demo2;
import java.util.*;

//DFS search for eight puzzle
public class DFSEP {
	public static void search(List<String> input){
		//set timer
		long startTime = System.currentTimeMillis();	
		int[] board = cast(input);
		State root = new State(new EightPuzzle(board));	
		//using sum of inversions to check whether the puzzle is solvable
		if((root.getCurState().getSOI()%2) == 0){		
		//by using stack we can do such operation: 
        //remove leftmost element from open list,add successors to the left most of open list
			Stack<State> stack = new Stack<State>();
		    stack.add(root);
		    search(stack);
		    long endTime = System.currentTimeMillis();
		    long totalTime = endTime - startTime;
		    System.out.println("Running time is:\n" + totalTime + "milliseconds");
		}
		else{
			System.out.println("Sorry,the puzzle is not solvable...");
		}
	}
	
	
	//implement the search	
	public static void search(Stack<State> s){
		while (!s.isEmpty()){	
			//current position pointer, take the top of the stack
			State curState = (State) s.pop();
			if (!curState.getCurState().isGoal()){
				ArrayList<EightPuzzle> candidates = curState.getCurState().getSuccessors();
				//check all the successors,if it's never been visited add it to the open list
				for (int i = 0; i < candidates.size(); i++){
					State t = new State(curState,candidates.get(i), curState.getACost()+ candidates.get(i).costs(), 0);
					
					if (!visit(t)){
						//add successors to the left most of open list
						s.add(t);		
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
				//push the root,AKA the goal
				solutionStack.push(curState);
				
				int size = solutionStack.size();
				
				//to print the result
				for (int i = 0; i < size; i++){
					curState = solutionStack.pop();
					curState.getCurState().printBoards();
					System.out.println();
					System.out.println();
				}
				break;
			}
		}

		//System.out.println("Error! No solution found!");
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
	
	private static boolean  visit(State s){
		boolean visited = false;
		State curState = s;
		
		while (s.getParent() != null && !visited){
			if (s.getParent().getCurState().equals(curState.getCurState())){
				visited = true;
			}
			s = s.getParent();			
		}	
		return visited;
		
	}
}
