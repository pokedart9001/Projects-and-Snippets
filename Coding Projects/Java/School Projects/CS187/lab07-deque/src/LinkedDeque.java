import java.util.NoSuchElementException;

public class LinkedDeque<T> implements Deque<T> {

	private DLNode<T> head;
	private DLNode<T> tail;

	@Override
	public void addToFront(T element) {
		head = new DLNode<T>(element, head, null);
		if (head.next == null) {
			tail = head;
		} else {
			head.next.prev = head;
		}
	}

	@Override
	public T removeFront() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T data = head.data;
		if (head.next != null) {
			head.next.prev = null;
		} else {
			tail = null;
		}
		head = head.next;
		return data;
	}

	@Override
	public T first() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return head.data;
	}

	@Override
	public void addToRear(T element) {
		tail = new DLNode<T>(element, null, tail);
		if (tail.prev == null)
			head = tail;
		else
			tail.prev.next = tail;
	}

	@Override
	public T removeRear() throws NoSuchElementException {
		T data = last();
		if (tail.prev != null)
			tail.prev.next = null;
		else
			head = null;
		tail = tail.prev;
		return data;
	}

	@Override
	public T last() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		return tail.data;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

}