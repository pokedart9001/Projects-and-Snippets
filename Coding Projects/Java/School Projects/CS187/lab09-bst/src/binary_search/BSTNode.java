package binary_search;

public class BSTNode<T extends Comparable<T>> {
	/* the data members are all declared as public
	 * so you can access them directly
	 */
	public T data;
	public BSTNode<T> left;
	public BSTNode<T> right;

	public BSTNode() {
		this(null, null, null);
	}
	
	public BSTNode(T data) {
		this(data, null, null);
		
	}
	public BSTNode(T data, BSTNode<T> left, BSTNode<T> right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}	
}