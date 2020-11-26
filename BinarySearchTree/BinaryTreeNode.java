package TreeOperations;

public class BinaryTreeNode {
	private String value = null;
	private BinaryTreeNode left =null;
	private BinaryTreeNode right =null;
	private BinaryTreeNode parent =null;
	private int counter = 0;
	private int depth = 0;
	
	public BinaryTreeNode(String value, BinaryTreeNode left, BinaryTreeNode right, BinaryTreeNode parent, int counter, int depth) {
		super();
		this.value = value;
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.counter = counter;
		this.depth = depth;
	}
	
	public BinaryTreeNode(String value) {
		this.value = value;
		this.counter = counter;
	}
	
	public BinaryTreeNode() {
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public BinaryTreeNode getLeft() {
		return left;
	}
	public void setLeft(BinaryTreeNode left) {
		this.left = left;
	}
	public BinaryTreeNode getRight() {
		return right;
	}
	public void setRight(BinaryTreeNode right) {
		this.right = right;
	}
	public BinaryTreeNode getParent() {
		return parent;
	}
	public void setParent(BinaryTreeNode parent) {
		this.parent = parent;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
}
