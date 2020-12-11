/**
 * Lab 10 - Midterm Review Practice Exercise
 *
 * Q1. Tree Traversal
 *   - Preorder traversal
 *   - Inorder traversal
 *   - Postorder traversal
 */
import java.util.Stack;

class Node {
	int data;
	Node left, right;

	public Node(int val) {
		data = val;
		left = right = null;
	}

	public String toString() {
		return "" + data;
	}
}

public class BinaryTree {
	// global variable
	Node root;

	// constructor
	BinaryTree() {
		root = null;
	}

	/**
	 * TODO: Given a binary tree, print its nodes according to the "bottom-up"
	 * postorder traversal.
	 */
	void printPostOrder(Node n) {
		if (n != null) {
			printPostOrder(n.left);
			printPostOrder(n.right);
			System.out.print(n.data + " ");
		}
	}

	/**
	 * TODO: Given a binary tree, print its nodes in inorder
	 */
	void printInOrder(Node n) {
		if (n != null) {
			printInOrder(n.left);
			System.out.print(n.data + " ");
			printInOrder(n.right);
		}
	}

	/**
	 * TODO: Given a binary tree, print its nodes in preorder
	 */
	void printPreOrder(Node n) {
		if (n != null) {
			System.out.print(n.data + " ");
			printPreOrder(n.left);
			printPreOrder(n.right);
		}
	}

	void post_order_iterative(Node root) {
		if (root == null)
			return;
		Stack<Node> nodes = new Stack<Node>();
		Stack<Node> printing = new Stack<Node>();
		nodes.push(root);
	
		while (!nodes.isEmpty()) {
			Node n = nodes.pop();
			printing.push(n);
			if (n.left != null)
				nodes.push(n.left);
			if (n.right != null)
				nodes.push(n.right);
		}
		while (!printing.isEmpty())
			System.out.print(printing.pop().data + " ");
	}

	void in_order_iterative(Node root) {
		if (root == null)
			return;
		Stack<Node> nodes = new Stack<Node>();
		nodes.push(root);
		
		while (!nodes.isEmpty()) {
			Node n = nodes.peek();
			if (n.left != null)
				nodes.push(n.left);
			else {
				while (n.right == null && !nodes.isEmpty()) {
					n = nodes.pop();
					System.out.print(n.data + " ");
				}
				if (n.right != null)
					nodes.push(n.right);
			}
		}
	}

	// Driver program
	public static void main(String[] args) {
		BinaryTree t = new BinaryTree();
		t.root = new Node(1);
		t.root.left = new Node(2);
		t.root.right = new Node(3);
		t.root.left.left = new Node(4);
		t.root.left.right = new Node(5);

		System.out.println("Recursive Preorder traversal of binary tree is ");
		t.printPreOrder(t.root);

		System.out.println("\n Inorder traversal of binary tree is ");
		System.out.println("For recursive: ");
		t.printInOrder(t.root);

		System.out.println("For iterative: ");
		t.in_order_iterative(t.root);

		System.out.println("\nPostorder traversal of binary tree is ");
		System.out.println("For recursive: ");
		t.printPostOrder(t.root);

		System.out.println("For iterative: ");
		t.post_order_iterative(t.root);

	}
}