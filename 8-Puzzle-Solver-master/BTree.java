package demo2;

public class BTree {
	private BTree parent;
	private BTree leftChild;
	private BTree rightChild;
	private int data;

	
	//initial constructor
	public BTree(int d){
		data = d;
	}
	
	//root node constructor
	public BTree(int d,BTree l,BTree r){
		data = d;
		leftChild = l;
		rightChild = r;

	}
	
	//interior node constructor
	public BTree(int d,BTree p,BTree l,BTree r){
		data = d;
		parent = p;
		leftChild = l;
		rightChild = r;

	}
	
	//interior node constructor
	public BTree(int d,BTree p){
		data = d;
		parent = p;

	}
	
	
	public int getData(){
		return data;
	}
	

	
	public BTree getParent(){
		return parent;
	}

	public BTree getLeftChild(){
		return leftChild;
	}
	
	public BTree getRightChild(){
		return rightChild;
	}
	

}
