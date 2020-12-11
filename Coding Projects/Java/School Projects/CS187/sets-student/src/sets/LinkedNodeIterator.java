package sets;

import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedNodeIterator<E> implements Iterator<E> {
	public LinkedNode<E> head;

	// Constructors
	public LinkedNodeIterator(LinkedNode<E> head) {
		this.head = head;
	}

	@Override
	public boolean hasNext() {
		return head != null;
	}

	@Override
	public E next() {
		if (!hasNext())
			throw new NoSuchElementException();
		E result = head.getData();
		head = head.getNext();
		return result;
	}

	@Override
	public void remove() {
		// Nothing to change for this method
		throw new UnsupportedOperationException();
	}
}
