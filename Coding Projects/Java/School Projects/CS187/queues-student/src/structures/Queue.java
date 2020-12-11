package structures;

import java.util.NoSuchElementException;

/**************************************************************************************
 * NOTE: before starting to code, check
 * support/structures/UnboundedQueueInterface.java for detailed explanation of
 * each interface method, including the parameters, return values, assumptions,
 * and requirements
 ***************************************************************************************/
public class Queue<T> implements UnboundedQueueInterface<T> {

	private Node<T> head;
	private Node<T> tail;
	private int size;

	public Queue() {
		head = null;
		tail = null;
		size = 0;
	}

	public Queue(Queue<T> other) {
		this();
		for (Node<T> node = other.head; node != null; node = node.next)
			enqueue(node.data);
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void enqueue(T element) {
		Node<T> node = new Node<T>(element);
		if (head == null)
			head = node;
		else
			tail.next = node;
		tail = node;
		size++;
	}

	@Override
	public T dequeue() throws NoSuchElementException {
		T element = peek();
		head = head.next;
		size--;
		return element;
	}

	@Override
	public T peek() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		return head.data;
	}

	@Override
	public UnboundedQueueInterface<T> reversed() {
		if (size < 2) return new Queue<T>(this);
		UnboundedQueueInterface<T> result = new Queue<T>(this);
		T element = result.dequeue();
		result = result.reversed();
		result.enqueue(element);
		return result;
	}
}

class Node<T> {
	public T data;
	public Node<T> next;

	public Node(T data) {
		this.data = data;
	}

	public Node(T data, Node<T> next) {
		this.data = data;
		this.next = next;
	}
}
