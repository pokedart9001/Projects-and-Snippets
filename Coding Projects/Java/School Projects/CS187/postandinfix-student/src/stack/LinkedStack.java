package stack;

/**
 * A {@link LinkedStack} is a generic stack that is implemented using a Linked
 * List structure to allow for unbounded size.
 */
public class LinkedStack<T> {

	private LLNode<T> head;
	private int size;

	/**
	 * Remove and return the top element on this stack. If stack is empty, return
	 * null (instead of throw exception)
	 */
	public T pop() {
		T top = top();
		if (head != null) {
			head = head.link;
			size--;
		}
		return top;
	}

	/**
	 * Return the top element of this stack (do not remove the top element). If
	 * stack is empty, return null (instead of throw exception)
	 */
	public T top() {
		return head == null ? null : head.info;
	}

	/**
	 * Return true if the stack is empty and false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Return the number of elements in this stack.
	 */
	public int size() {
		return size;
	}

	/**
	 * Pushes a new element to the top of this stack.
	 */
	public void push(T elem) {
		head = new LLNode<T>(elem, head);
		size++;
	}

}
