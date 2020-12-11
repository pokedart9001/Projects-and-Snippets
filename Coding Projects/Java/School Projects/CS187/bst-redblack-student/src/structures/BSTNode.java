package structures;

/**
 * A node in a BST.
 * 
 * Note that BSTNode MUST implement BSTNodeInterface; removing this will resulit
 * in your program failing to compile for the autograder.
 * 
 * @author liberato
 *
 * @param <T>
 */
public class BSTNode<T extends Comparable<T>> implements BSTNodeInterface<T> {
	private T data;
	private BSTNode<T> left;
	private BSTNode<T> right;
	private BSTNode<T> parent;
	
	public static final boolean RED   = true;
    public static final boolean BLACK = false;
	private boolean color;

	public BSTNode(T data, BSTNode<T> left, BSTNode<T> right) {
		this.data = data;
		this.left = left;
		this.right = right;
		parent = null;
	}
	
	public BSTNode(T data, BSTNode<T> left, BSTNode<T> right, boolean color) {
		this.data = data;
		this.left = left;
		this.right = right;
		this.color = color;
		parent = null;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public boolean getColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	public BSTNode<T> getLeft() {
		return left;
	}

	public void setLeft(BSTNode<T> left) {
		this.left = left;
	}

	public BSTNode<T> getRight() {
		return right;
	}

	public void setRight(BSTNode<T> right) {
		this.right = right;
	}
	
	public BSTNode<T> getParent() {
		return parent;
	}
	
	public void setParent(BSTNode<T> p) {
		this.parent = p;
	}
	
	public BSTNode<T> getGrandParent() {
		return isRoot() ? null : parent.parent;
	}
	
	public BSTNode<T> getUncle() {
		BSTNode<T> gp = getGrandParent();
		return gp == null ? null : parent == gp.left ? gp.right : gp.left;
	}
	
	public boolean isLeaf() {
		return left == null && right == null;
	}
	
	public boolean isRoot() {
		return parent == null;
	}
	
	public boolean isInside() {
		BSTNode<T> gp = getGrandParent();
		if (gp == null)
			throw new NullPointerException();
		if (gp.right == null)
			return this == gp.left.right;
		if (gp.left == null)
			return this == gp.right.left;
		return this == gp.right.left || this == gp.left.right;
	}
	
	public void printSubtree(int spaces) {
		if (right != null) {
			right.printSubtree(spaces + 5);
		}
		for (int i = 0; i < spaces; i++) {
			System.out.print(" ");
		}
		System.out.print(data);
		System.out.print('-');
		System.out.println(color);
		if (left != null) {
			left.printSubtree(spaces + 5);
		}
	}
}