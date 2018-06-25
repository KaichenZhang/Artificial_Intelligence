package demo2;
import java.util.*;

//the representation for each board 
public class EightPuzzle {
	private final int PUZZLE_SIZE = 9;
	private final List<String> GOAL = Arrays.asList(new String[]{"1","2","3","8","B","4","7","6","5"});
	private int misplacedTiles = 0;
	private int manDist = 0;
	private double SLDist = 0;
	//take this as the admissible heuristic,and if sumOfInversions is even the puzzle is not solvable
	private int sumOfInversions = 0; 
	private int[] curBoard;


	//constructor
	public EightPuzzle(int[] board){
		curBoard = board;
		MisplacedTilesCounter();
		ManDistCounter();
		AdmissibleHeuristic();
		SumOfInversions();
	}
	
	
	
	//each step costs one
	public int costs(){
		return 1;
	}

	//calculate the number of misplaced tiles
	private void MisplacedTilesCounter(){
		for (int i = 0; i < curBoard.length; i++){
			if (curBoard[i] != cast(GOAL)[i]){
				misplacedTiles++;
			}
		}
	}

	//calculate the manhattan distance
	private void ManDistCounter(){
		//pointer to the checkpoint
		int p = -1;
		for (int i = 0; i<curBoard.length; i++){
				p++;
				int goalIndex = find(cast(GOAL),curBoard[p]);
				//index of the current value which the pointer stays
				int index = (curBoard[p] - 1);
				//if it's not the blank
				if (index != -1){
					//calculate the horizontal coordinate
					int h = index % 3;
					int hBase = goalIndex % 3;
					//calculate the vertical coordinate
					int v = index / 3;
					int vBase = goalIndex / 3;
					manDist += Math.abs(v - vBase) + Math.abs(h - hBase);			
				}
		}
	}
		
	//calculate the Straight Line Distance
	private void AdmissibleHeuristic(){
		//pointer to the checkpoint
		int p = -1;
		for (int i = 0; i<curBoard.length; i++){
				p++;
				int goalIndex = find(cast(GOAL),curBoard[p]);
				//index of the current value which the pointer stays
				int index = (curBoard[p] - 1);
				//if it's not the blank
				if (index != -1){
					//calculate the horizontal coordinate
					int h = index % 3;
					int hBase = goalIndex % 3;
					//calculate the vertical coordinate
					int v = index / 3;
					int vBase = goalIndex / 3;
					//calculate the Straight Line Distance
					SLDist += Math.sqrt(Math.pow((v - vBase),2) + Math.pow((h - hBase),2));			
				}
		}
	}
		
	//find the index of specific value
	public int find(int[] array,int value){
		return Arrays.asList(array).indexOf(value);
	}
	
	//calculate the sum of permutation inversions
    //Algorithm:sum the number of tiles,which should be located at the left side of goal state, but now stays at the right side in the current state board.
    //The heuristic is inadmissible because that the sum of permutation inversions could overestimate the cost

    //line the elements with ascending value, and counting the sum of how many tiles are inversed 
	//comparition in the puzzle start from left top,process in clock direction,end in the middle
	private void SumOfInversions(){
		
		int[] linedBoardState = {curBoard[0],curBoard[1],curBoard[2],curBoard[5],curBoard[8],curBoard[7],curBoard[6],curBoard[3],curBoard[4]};
		 //change the blank to the last position of the array, and assign the blank with value 9
		for(int v = 0;v<linedBoardState.length-1;v++){  
			if(linedBoardState[v] == 0) {
				for(int k = v; k < linedBoardState.length-1; k++){
					linedBoardState[k] = linedBoardState[k+1];
				}
			}		
		}
		linedBoardState[curBoard.length-1] = 9;		
	
		//the the lined goal is {1, 2, 3, 4, 5, 6, 7, 8, 9}, which is equivalent to the 
		for (int i = 0; i < curBoard.length - 1; i++){
			for(int j = i+1; j < curBoard.length; j++){
				if(linedBoardState[i] > linedBoardState[j]){
					sumOfInversions++;
				}				
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


	//return the current board integer array
	public int[] getCurBoard(){
		return curBoard;
	}
	
	//locate the blank and return the index of blank
	private int getBlankIndex(){
		int blankIndex = -1;
		for (int i = 0; i < PUZZLE_SIZE; i++){
			if (curBoard[i] == 0)
				blankIndex = i;
		}
		return blankIndex;
	}

	//return the counter of  misplaced tiles
	public int getMisplacedTiles(){
		return misplacedTiles;
	}

	//return the counter of  Manhattan distance
	public int getManDist(){
		return manDist;
	}
	
	public double getAdmissibleDist(){
		return SLDist;
	}
	
	//return the sum of inversions
	//to check whether a puzzle is solvable
	public int getSOI(){
		return sumOfInversions;
	}
	
	//return the successors of current puzzle state
	public ArrayList<EightPuzzle> getSuccessors(){
		ArrayList<EightPuzzle> successors = new ArrayList<EightPuzzle>();
		//index of the blank
		int index = getBlankIndex();

		//if the blank is not on the left most side
		if (index != 0 && index != 3 && index != 6){
			//we can move to the left side
			moveTo(index - 1, index, successors);
		}
		//if the blank is not on the right most side
		if (index != 2 && index != 5 && index != 8){
			//we can move to the right side 
			moveTo(index + 1, index, successors);
		}
		//if the blank is not on the top
		if (index != 0 && index != 1 && index != 2){
			moveTo(index - 3, index, successors);
		}
		//if the blank is not on the bottom
		if (index != 6 && index != 7 && index != 8){
			moveTo(index + 3, index, successors);
		}
		
		return successors;
	}

	

	//auxiliary method for getSuccessors(),to simulate the movement of tails
	private void moveTo(int i, int j, ArrayList<EightPuzzle> s){
		int[] t= new int[PUZZLE_SIZE];
		for (int k = 0; k < PUZZLE_SIZE; k++){
			t[k] = curBoard[k];
		}
		int temp = t[i];
		t[i] = curBoard[j];
		t[j] = temp;
		s.add((new EightPuzzle(t)));
	}

	//to check whether the current state is the goal state.
	public boolean isGoal(){
		if (Arrays.equals(curBoard,cast(GOAL))){
			return true;
		}
		return false;
	}

	// to print the current board
	public void printBoards(){
		System.out.println(curBoard[0] + "  " + curBoard[1] + "  "+ curBoard[2]);
		System.out.println(curBoard[3] + "  " + curBoard[4] + "  "+ curBoard[5]);
		System.out.println(curBoard[6] + "  " + curBoard[7] + "  "+ curBoard[8]);
	}



	//Overloaded equals, to compare whether two states are the same
	public boolean equals(EightPuzzle s){
		if (Arrays.equals(curBoard, ((EightPuzzle) s).getCurBoard())){
			return true;
		}
		else
			return false;
	}
}
