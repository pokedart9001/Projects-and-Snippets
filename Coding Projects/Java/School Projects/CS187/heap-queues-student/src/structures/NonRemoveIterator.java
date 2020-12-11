package structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class NonRemoveIterator<E> implements Iterator<E> {

	ArrayList<E> list;
	int index;
	
	public NonRemoveIterator(ArrayList<E> list) {
		this.list = list;
		index = 0;
	}
	
	@Override
	public boolean hasNext() {
		return index < list.size();
	}

	@Override
	public E next() {
		if (!hasNext())
			throw new NoSuchElementException();
		return list.get(index++);
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
