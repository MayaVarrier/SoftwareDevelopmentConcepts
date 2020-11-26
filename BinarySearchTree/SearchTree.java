package TreeOperations;
import java.io.*;

public class SearchTree {

	int dep = 1;
	String key;
	String con = "";
	BinaryTree bt = new BinaryTree();
	BinaryTreeNode btn = new BinaryTreeNode();
	
	public SearchTree() {
		super();
	}

	//Constructor to get the element 
	public SearchTree(String key) {
		super();
		this.key = key;
	}
	
	//Add an element to the tree
	boolean add( String key )
	{
		boolean ret = false;
		int depth = 1;
		
		try {
		//Create a new node with "key" as the value
			if((key!= null) && (!key.isEmpty())) {
		BinaryTreeNode btn1 = new BinaryTreeNode(key);
		
		//If the tree doesn't exist, create it and assign value to root
		if(bt.getRoot() == null) {
			btn1.setDepth(depth);
			bt.setRoot(btn1);
			ret = true;
		}
		else {
			BinaryTreeNode b = new BinaryTreeNode(null);
			BinaryTreeNode c = bt.getRoot();
			
			//Find the leaf node where new node should be added to
			while(c!=null) {
				b=c;
				depth++;
				String str1 = key;
				String str2 = c.getValue();
			
				//If the key is an integer value, then convert it to integer 
				try {
					int s1 = Integer.parseInt(str1);
					int s2 = Integer.parseInt(str2);
				
					//If the key is lesser than node,then go to the left to the node	
					if(s1 < s2) {
						c = c.getLeft();
					}
					//If the key is greater than node,then go to the right to the node
					else if (s1 > s2){
						c = c.getRight();
					}
					//If the key is same as the node, then its a duplicate value and don't add to the tree
					else if (s1 == s2) {
						System.out.println("The value is already present in the tree!");
						c = null;
					}
				}
				catch(NumberFormatException e)
				{
					//If the key is lesser than node,then go to the left to the node
					if(key.compareToIgnoreCase(c.getValue()) < 0) {
						c = c.getLeft();
					}
					//If the key is greater than node,then go to the right to the node
					else if (key.compareToIgnoreCase(c.getValue()) > 0){
						c = c.getRight();
					}
					//If the key is same as the node, then its a duplicate value and don't add to the tree
					else if (key.compareToIgnoreCase(c.getValue()) == 0) {
						System.out.println("The value is already present in the tree!");
						c = null;
					}
				}
				
			}
			
			//Compare the key with the leaf node. If the key is smaller, then add it to the left
			if(key.compareToIgnoreCase(b.getValue()) < 0) {
				b.setLeft(btn1);
				b.getLeft().setDepth(depth);
				btn1.setParent(b);
				depth = 0;
				ret = true;
			}
			//Compare the key with the leaf node. If the key is greater, then add it to the right
			else if(key.compareToIgnoreCase(b.getValue()) > 0) {
				b.setRight(btn1);
				b.getRight().setDepth(depth);
				btn1.setParent(b);
				depth = 0;
				ret = true;
			}
		}
			}
		}
		catch(NullPointerException e) {
			System.out.println("Please enter a valid value!");
		}
		return ret;
	}
	//Find an element in the tree
	int find( String key ) {
		int d = 0;
		
		BinaryTreeNode temp = new BinaryTreeNode();
		BinaryTreeNode btn1 = bt.getRoot(); 
		
		//Parse through the tree to find where the key is
		if((key != null) && (!key.isEmpty())) {
		while ((btn1 != null) && (btn1.getValue() != key)) {
			if(key.compareToIgnoreCase(btn1.getValue()) < 0) {
				btn1 = btn1.getLeft();
			}
							
			else if (key.compareToIgnoreCase(btn1.getValue()) > 0) {
			btn1 = btn1.getRight();
		    }
			else {
				break;
			}
		}
		
		//If the key is not found, then the key is not present in the tree
		if (btn1 == null) {
			d=0;
		} 
		//If the key is the root, then increment the number of times the node has been searched by 1
		else if(btn1 == bt.getRoot()) {
			d = btn1.getDepth();
			btn1.setCounter(btn1.getCounter()+1);
		}
		
		else {
			btn1.setCounter(btn1.getCounter()+1);
			//If the number of times the key is searched more than the counter, then do the following
			if(btn1.getCounter() > btn1.getParent().getCounter()){
				//If the key is the left child, then swap the parent and the left child.
				if(btn1 == btn1.getParent().getLeft()) {
					temp = btn1.getRight();
					//Set the parent as the right child of the key
					btn1.setRight(btn1.getParent());
					btn1.getRight().setLeft(temp);
					
					//Set the pointer from parent's parent to the key
					if(btn1.getParent().getParent() != null) {
						if(btn1.getParent() == btn1.getParent().getParent().getLeft()) {
							btn1.getParent().getParent().setLeft(btn1);
						}
						else {
							btn1.getParent().getParent().setRight(btn1);
						}
						btn1.setParent(btn1.getParent().getParent());
					}
					//If parent was a root, then set the key to root
					else {
						btn1.getRight().setParent(btn1);
						btn1.setParent(null);
						bt.setRoot(btn1);
					}
					//Calculate the new depth
					dep = 1;
					setDepth(bt.getRoot());
				}
				//If the key is the right child, then swap the parent and the right child.
				else if(btn1 == btn1.getParent().getRight()) {
					temp = btn1.getLeft();
					//Set the parent as the left child of the key
					btn1.setLeft(btn1.getParent());
					btn1.getLeft().setRight(temp);
					
					//Set the pointer from parent's parent to the key
					if(btn1.getParent().getParent() != null) {
						if(btn1.getParent() == btn1.getParent().getParent().getLeft()) {
							btn1.getParent().getParent().setLeft(btn1);
						}
						else {
								btn1.getParent().getParent().setRight(btn1);
						}
						btn1.setParent(btn1.getParent().getParent());
					}
					//If parent was a root, then set the key to root
					else {
						btn1.getLeft().setParent(btn1);
						btn1.setParent(null);
						btn1 = bt.getRoot();
					}
					dep = 1;
					setDepth(bt.getRoot());
				}
			}
			d = btn1.getDepth();
		}	
		}
		return d;
		
	}
	
	//reset counter values
	void reset() {
		//If its an empty tree
		if(bt.getRoot() == null) {
			System.out.println("The tree is empty!");
		}
		else {
		//Set the counters of all nodes to 0
		delete(bt.getRoot());	
			}
	}
	
	//Print the elements of the tree
	String printTree() {
		
		String content = null;
		//If its an empty tree
		if(bt.getRoot() == null) {
			System.out.println("Its an empty tree!");
		}
		else {
			//Get the value and depth of the tree
			content = traverse(bt.getRoot());
		}
		con = "";
		return content;
		
	}

	//Traverse the tree and return node value and depth
	public String traverse(BinaryTreeNode b) {
		
		String value = null;
		String c = null;
		int dep = 0;
		//Traverse all the left nodes
		if(b.getLeft()!= null) {
			traverse(b.getLeft());
		}
		//Display the value of the node and its depth
		value = b.getValue();
		dep = b.getDepth();
		c = new String(value + " " + dep + "\n");
		con = con.concat(c);
		//Traverse all the right nodes
		if(b.getRight()!= null) {
			traverse(b.getRight());
		}
		
		return con;
	}
	
	//Set counter values to 0
	public void delete(BinaryTreeNode b) {
		//Traverse all the left nodes and set their counters to 0
		if(b.getLeft()!= null) {
			b.setCounter(0);
			delete(b.getLeft());
		}
		
		b.setCounter(0);
		//Traverse all the right nodes and set their counters to 0
		if(b.getRight()!= null) {
			b.setCounter(0);
			delete(b.getRight());
		}
		
	}

    void setDepth(BinaryTreeNode b) {
    	//Traverse all the left nodes and set their depths
		if(b.getLeft()!= null) {
			dep++;
			setDepth(b.getLeft());
		}
		
		b.setDepth(dep);
		//Traverse all the right nodes and set their depths
		if(b.getRight()!= null) {
			dep++;
			setDepth(b.getRight());
		}
		dep--;
	}

}
