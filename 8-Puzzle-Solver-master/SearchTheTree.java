package demo2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SearchTheTree {
	private final static int GOAL = 12; 
	
	public static ArrayList<BTree> initialTree(){
		//nodes initialization
		ArrayList<BTree> nodes = new ArrayList<BTree>();
		for(int i=0;i<15;i++){
			nodes.add(new BTree(i+1));			
		}
		
		ArrayList<BTree> tree = new ArrayList<BTree>();
		//to add the root
		tree.add(new BTree(nodes.get(0).getData(), nodes.get(1), nodes.get(2)));
		
		//to add the interior leaves
		for(int i = 1;i<7;i++){
			tree.add(new BTree(nodes.get(i).getData(), nodes.get((int)Math.floor((i-1)/2)),nodes.get(2*i+1), nodes.get(2*i+2)));
		}
		
		//to add the bottom leaves
		for(int i = 7;i<15;i++){
			tree.add(new BTree(nodes.get(i).getData(), nodes.get((int)Math.floor((i-1)/2))));
		}
		System.out.println("The binary tree is:");
		System.out.println("	 *                                                          1");
		System.out.println("	 *                                                        /   \\");
		System.out.println("	 *                                                     2          3");
		System.out.println("	 *                                                    / \\        / \\");
		System.out.println("	 *                                                  4    5      6    7");
		System.out.println("	 *                                                 / \\  / \\    / \\  / \\");
		System.out.println("	 *                                                8  9  10 11 12 13 14 15");
		return tree;		
	}
	
	//BFS
	public static void BFS(ArrayList<BTree> bTree){
		//open list
		Queue<BTree> queue = new LinkedList<BTree>();
		//add the root
		queue.add(bTree.get(0));
		System.out.println("Search goes throgh");
		while ((!queue.isEmpty())&&(queue.peek().getData()!=GOAL)){
			BTree pointer = queue.poll();
			System.out.println("--" + pointer.getData() + "--" );
			int index = pointer.getData()-1;
			
			if(pointer.getLeftChild() != null){
				//tree information is stored in ArrayList bTree 
				queue.add(bTree.get(2*index+1));				
			}		
			if(pointer.getRightChild() != null){
				queue.add(bTree.get(2*index+2));				
			}						
		}		
	}
	
	
	//DFS
	public static void DFS(ArrayList<BTree> bTree){
		//open list
		Stack<BTree> stack = new Stack<BTree>();
		//add the root
		stack.add(bTree.get(0));
		System.out.println("Search goes throgh");
		while ((!stack.isEmpty())&&(stack.peek().getData()!=GOAL)){
			BTree pointer = stack.pop();
			System.out.println("--" + pointer.getData() + "--" );
			int index = pointer.getData()-1;
			if(pointer.getRightChild() != null)
				stack.add(bTree.get(2*index+2));	
			if(pointer.getLeftChild() != null)
				//tree information is stored in ArrayList bTree 
				stack.add(bTree.get(2*index+1));				
							
			
			
								
			
		}		
	}

}
