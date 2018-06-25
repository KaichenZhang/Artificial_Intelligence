package demo2;

import java.util.*;


public class Demo {
	public static void main(String[] args){
		//input as a list '(2 ,8 ,3 ,1 ,6 ,4 ,7 ,B ,5)
		String[] app = new String[]{"2","8","3","1","6","4","7","B","5"};
		//String[] app = new String[]{"3","7","5","8","2","1","4","B","6"};
		//String[] app = new String[]{"4","3","5","7","B","1","2","8","6"};
		//String[] app = new String[]{"7","2","5","4","3","1","6","8","B"};
		//String[] app = new String[]{"5","4","1","3","6","2","7","B","8"};
		//String[] app = new String[]{"3","2","B","4","1","5","7","6","8"};
		//String[] app = new String[]{"4","7","5","2","B","6","8","3","1"};
		//String[] app = new String[]{"7","5","6","4","3","2","8","1","B"};
		
		
		List<String> inputList = Arrays.asList(app);
	
		//choice index
		int userChoice=0;
		Scanner read = new Scanner(System.in);
		
		showMenu();
		
		while(true){
			try{
				userChoice=read.nextInt();
			}catch(Exception e){
				System.out.println("Invalid Input, please enter an Integer");
				read.nextLine();
			}
		
		
		switch(userChoice){
		case 1: 
			System.out.println("Solved the puzzle with BFS");
			System.out.println("Please  wait.");
			BFSEP.search(inputList);
			showMenu();
			break;
		case 2:
			System.out.println("Solving the puzzle with DFS");
			System.out.println("Please  wait.");
			DFSEP.search(inputList);
			showMenu();
			break;
		case 3:
			System.out.println("Solving the puzzle with A* Search Heuristic:Misplaced Tiles");
			System.out.println("Please  wait.");
			AStarSearchEP.search(inputList, 0);
			showMenu();
			break;
		case 4:
			System.out.println("Solving the puzzle with A* Search Heuristic:Manhattan Distance");
			System.out.println("Please  wait.");
			AStarSearchEP.search(inputList, 1);
			showMenu();
			break;
		case 5:
			System.out.println("Solving the puzzle with A* Search Heuristic:Min(MD,MT)");
			System.out.println("Please  wait.");
			AStarSearchEP.search(inputList, 2);
			showMenu();
			break;
		case 6:
			System.out.println("Solving the puzzle with In Admissible Heuristic:Sum of Permutation Inversions");
			System.out.println("Please  wait.");
			InAdmissibleEP.search(inputList);
			showMenu();
			break;
		case 7:
			System.out.println("Solving the puzzle with A* Search Heuristic:Straight Line Distance");
			System.out.println("Please  wait.");
			AStarSearchEP.search(inputList,3);
			showMenu();
			break;
		case 8:
			System.out.println("Best First Search");
			System.out.println("Please  wait.");
			GreadySearchEP.search(inputList);
			showMenu();
			break;
		case 9:
			System.out.println("General Test For BFS");
			System.out.println("Please  wait.");
			SearchTheTree.BFS(SearchTheTree.initialTree());
			showMenu();
			break;
		case 10:
			System.out.println("General Test For DFS");
			System.out.println("Please  wait.");
			SearchTheTree.DFS(SearchTheTree.initialTree());
			showMenu();
			break;
		case 11:
			System.out.println("Successful end the solver.");
			read.close();
			System.exit(0);
		default:
			System.out.println("Invalid Input, please follow the guidance.");
			}
		}
		}
		
	
	public static void showMenu(){
		System.out.println("----------------------------------");
		System.out.println("-------Eight puzzle solver--------");
		System.out.println("----------------------------------");
		System.out.println("Please select a search type");
		System.out.println("1.  BFS");
		System.out.println("2.  DFS");
		System.out.println("3.  A* Search with heuristic:Manhattan Distance");
		System.out.println("4.  A* Search with heuristic:Misplaced Tiles");
		System.out.println("5.  A* Search with heuristic:Min(Manhattan Distance,Misplaced Tiles)");
		System.out.println("6.  (InAdmissible)Sum of Permutation Inversions");
		System.out.println("7.  (Admissible)A* Search with heuristic:Straight Line Distance");
		System.out.println("8.  Best First Search");
		System.out.println("9.  General Test For BFS(From root to the GOAL with value 12)");
		System.out.println("10. General Test For DFS(From root to the GOAL with value 12)");
		System.out.println("11. EXIT");
	}
}
