package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>>
		implements
			BSTInterface<T> {
	protected BSTNode<T> root;

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return subtreeSize(root);
	}

	protected int subtreeSize(BSTNode<T> node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + subtreeSize(node.getLeft())
					+ subtreeSize(node.getRight());
		}
	}

	public boolean contains(T t) {
		if (t == null)
			throw new NullPointerException();
		return get(t) != null;
	}

	public boolean remove(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		boolean result = contains(t);
		if (result) {
			root = removeFromSubtree(root, t);
		}
		return result;
	}

	private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
		// node must not be null
		int result = t.compareTo(node.getData());
		if (result < 0) {
			node.setLeft(removeFromSubtree(node.getLeft(), t));
			if (node.getLeft() != null) {
				node.getLeft().setParent(node);
			}
			return node;
		} else if (result > 0) {
			node.setRight(removeFromSubtree(node.getRight(), t));
			if (node.getRight() != null) {
				node.getRight().setParent(node);
			}
			return node;
		} else { // result == 0
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else { // neither child is null
				T predecessorValue = getHighestValue(node.getLeft());
				node.setLeft(removeRightmost(node.getLeft()));
				if (node.getLeft() != null) {
					node.getLeft().setParent(node);
				}
				node.setData(predecessorValue);
				return node;
			}
		}
	}

	private T getHighestValue(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getData();
		} else {
			return getHighestValue(node.getRight());
		}
	}

	private BSTNode<T> removeRightmost(BSTNode<T> node) {
		// node must not be null
		if (node.getRight() == null) {
			return node.getLeft();
		} else {
			node.setRight(removeRightmost(node.getRight()));
			if (node.getRight() != null) {
				node.getRight().setParent(node);
			}
			return node;
		}
	}

	public T get(T t) {
		if (t == null)
			throw new NullPointerException();
		BSTNode<T> node = get(t, root);
		return node == null ? null : node.getData();
	}

	protected BSTNode<T> get(T t, BSTNode<T> node) {
		if (node == null)
			return null;
		if (node.getData().equals(t))
			return node;
		if (t.compareTo(node.getData()) > 0)
			return get(t, node.getRight());
		return get(t, node.getLeft());
	}

	public void add(T t) {
		if (t == null) {
			throw new NullPointerException();
		}
		root = addToSubtree(root, new BSTNode<T>(t, null, null));
	}

	protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
		if (node == null) {
			return toAdd;
		}
		int result = toAdd.getData().compareTo(node.getData());
		if (result <= 0) {
			node.setLeft(addToSubtree(node.getLeft(), toAdd));
			if (node.getLeft() != null) {
				node.getLeft().setParent(node);
			}
		} else {
			node.setRight(addToSubtree(node.getRight(), toAdd));
			if (node.getRight() != null) {
				node.getRight().setParent(node);
			}
		}
		return node;
	}

	@Override
	public T getMinimum() {
		return getMinMax(root, false);
	}

	@Override
	public T getMaximum() {
		return getMinMax(root, true);
	}

	private T getMinMax(BSTNode<T> node, boolean max) {
		if (isEmpty())
			return null;
		if (node.isLeaf())
			return node.getData();
		return max
				? getMinMax(node.getRight(), max)
				: getMinMax(node.getLeft(), max);
	}

	@Override
	public int height() {
		return height(root);
	}

	private int height(BSTNode<T> node) {
		return node == null
				? -1
				: 1 + Math.max(height(node.getLeft()), height(node.getRight()));
	}

	public Iterator<T> preorderIterator() {
		return orderIterator(0);
	}

	public Iterator<T> inorderIterator() {
		return orderIterator(1);
	}

	public Iterator<T> postorderIterator() {
		return orderIterator(2);
	}

	protected Iterator<T> orderIterator(int mode) {
		Queue<T> queue = new LinkedList<T>();
		orderTraverse(queue, root, mode);
		return queue.iterator();
	}

	private void orderTraverse(Queue<T> queue, BSTNode<T> node, int mode) {
		if (node != null) {
			switch (mode) {
				case 0 :
					queue.add(node.getData());
					orderTraverse(queue, node.getLeft(), mode);
					orderTraverse(queue, node.getRight(), mode);
					break;
				case 1 :
					orderTraverse(queue, node.getLeft(), mode);
					queue.add(node.getData());
					orderTraverse(queue, node.getRight(), mode);
					break;
				case 2 :
					orderTraverse(queue, node.getLeft(), mode);
					orderTraverse(queue, node.getRight(), mode);
					queue.add(node.getData());
					break;
			}

		}
	}

	@Override
	public boolean equals(BSTInterface<T> other) {
		return equals(root, other.getRoot());
	}

	private boolean equals(BSTNode<T> thisNode, BSTNode<T> otherNode) {
		if (thisNode == null)
			return otherNode == null;
		if (otherNode == null)
			return false;
		return thisNode.getData().equals(otherNode.getData())
				&& equals(thisNode.getLeft(), otherNode.getLeft())
				&& equals(thisNode.getRight(), otherNode.getRight());
	}

	@Override
	public boolean sameValues(BSTInterface<T> other) {
		Iterator<T> thisIterator = inorderIterator();
		Iterator<T> otherIterator = other.inorderIterator();

		while (thisIterator.hasNext() && otherIterator.hasNext())
			if (!thisIterator.next().equals(otherIterator.next()))
				return false;
		return !(thisIterator.hasNext() || otherIterator.hasNext());
	}

	@Override
	public boolean isBalanced() {
		int twoPowHeight = 1;
		int size = size();
		for (int i = 0; i < height(); i++)
			twoPowHeight *= 2;
		return twoPowHeight <= size && size < twoPowHeight * 2;
	}

	@Override
	@SuppressWarnings("unchecked")

	public void balance() {
		Iterator<T> iter = inorderIterator();
		T[] treeArray = (T[]) new Comparable[size()];
		for (int i = 0; iter.hasNext(); i++)
			treeArray[i] = iter.next();

		root = balance(treeArray, 0, treeArray.length - 1);
	}

	private BSTNode<T> balance(T[] treeArray, int start, int end) {
		if (start > end)
			return null;
		int mid = (start + end) / 2;
		BSTNode<T> node = new BSTNode<T>(treeArray[mid],
				balance(treeArray, start, mid - 1),
				balance(treeArray, mid + 1, end));
		if (node.getLeft() != null) node.getLeft().setParent(node);
		if (node.getRight() != null) node.getRight().setParent(node);
		return node;
	}

	@Override
	public BSTNode<T> getRoot() {
		// DO NOT MODIFY
		return root;
	}

	public static <T extends Comparable<T>> String toDotFormat(
			BSTNode<T> root) {
		// header
		int count = 0;
		String dot = "digraph G { \n";
		dot += "graph [ordering=\"out\"]; \n";
		// iterative traversal
		Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
		queue.add(root);
		BSTNode<T> cursor;
		while (!queue.isEmpty()) {
			cursor = queue.remove();
			if (cursor.getLeft() != null) {
				// add edge from cursor to left child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getLeft().getData().toString() + ";\n";
				queue.add(cursor.getLeft());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}
			if (cursor.getRight() != null) {
				// add edge from cursor to right child
				dot += cursor.getData().toString() + " -> "
						+ cursor.getRight().getData().toString() + ";\n";
				queue.add(cursor.getRight());
			} else {
				// add dummy node
				dot += "node" + count + " [shape=point];\n";
				dot += cursor.getData().toString() + " -> " + "node" + count
						+ ";\n";
				count++;
			}

		}
		dot += "};";
		return dot;
	}

	public static void main(String[] args) {
		for (String r : new String[]{"a", "b", "c", "d", "e", "f", "g"}) {
			BSTInterface<String> tree = new BinarySearchTree<String>();
			for (String s : new String[]{"d", "b", "a", "c", "f", "e", "g"}) {
				tree.add(s);
			}
			Iterator<String> iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.preorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
			iterator = tree.postorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();

			System.out.println(tree.remove(r));

			iterator = tree.inorderIterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next());
			}
			System.out.println();
		}

		BSTInterface<String> tree = new BinarySearchTree<String>();
		for (String r : new String[]{"a", "b", "c", "d", "e", "f", "g"}) {
			tree.add(r);
		}
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
		tree.balance();
		System.out.println(tree.size());
		System.out.println(tree.height());
		System.out.println(tree.isBalanced());
	}
}