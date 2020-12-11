package structures;

import java.util.Comparator;

public class StudentArrayHeap<P, V> extends AbstractArrayHeap<P, V> {

	protected StudentArrayHeap(Comparator<P> comparator) {
		super(comparator);
	}

	@Override
	protected int getLeftChildOf(int index) {
		if (index < 0)
			throw new IndexOutOfBoundsException();
		return 2 * index + 1;
	}

	@Override
	protected int getRightChildOf(int index) {
		if (index < 0)
			throw new IndexOutOfBoundsException();
		return 2 * index + 2;
	}

	private int getGreaterChildOf(int index) {
		int left = getLeftChildOf(index);
		int right = getRightChildOf(index);
		if (left >= heap.size()) {
			if (right >= heap.size()) {
				return -1;
			} else {
				return right;
			}
		} else if (right >= heap.size()) {
			return left;
		} else {
			return comparator.compare(heap.get(left).getPriority(),
					heap.get(right).getPriority()) > 0 ? left : right;
		}
	}

	@Override
	protected int getParentOf(int index) {
		if (index < 1)
			throw new IndexOutOfBoundsException();
		return (index - 1) / 2;
	}

	@Override
	protected void bubbleUp(int index) {
		if (index > 0) {
			P priority = heap.get(index).getPriority();
			P parentPriority = heap.get(getParentOf(index)).getPriority();

			if (comparator.compare(parentPriority, priority) < 0) {
				swap(index, getParentOf(index));
				bubbleUp(getParentOf(index));
			}
		}
	}

	@Override
	protected void bubbleDown(int index) {
		int next = getGreaterChildOf(index);
		if (next != -1) {
			P priority = heap.get(index).getPriority();
			P nextPriority = heap.get(next).getPriority();

			if (comparator.compare(nextPriority, priority) > 0) {
				swap(index, next);
				bubbleDown(next);
			}
		}
	}
}