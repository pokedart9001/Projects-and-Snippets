package structures;

import comparators.ReverseIntegerComparator;

import java.util.Comparator;
import java.util.Iterator;

public class MinQueue<V> implements PriorityQueue<Integer, V> {

	StudentArrayHeap<Integer, V> heap;

	public MinQueue() {
		heap = new StudentArrayHeap<Integer, V>(new ReverseIntegerComparator());
	}

	@Override
	public PriorityQueue<Integer, V> enqueue(Integer priority, V value) {
		if (priority == null || value == null)
			throw new NullPointerException();
		heap.add(priority, value);
		return this;
	}

	@Override
	public V dequeue() {
		if (isEmpty())
			throw new IllegalStateException();
		return heap.remove();
	}

	@Override
	public V peek() {
		if (isEmpty())
			throw new IllegalStateException();
		return heap.peek();
	}

	@Override
	public Iterator<Entry<Integer, V>> iterator() {
		return new NonRemoveIterator<Entry<Integer, V>>(heap.heap);
	}

	@Override
	public Comparator<Integer> getComparator() {
		return heap.getComparator();
	}

	@Override
	public int size() {
		return heap.size();
	}

	@Override
	public boolean isEmpty() {
		return heap.isEmpty();
	}
}