package structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import comparators.IntegerComparator;
import comparators.ReverseIntegerComparator;

public class PublicMaxQueueTest {

	MaxQueue<String> queue;

	@Before
	public void setup() {
		queue = new MaxQueue<String>();
	}

	@Test(timeout = 100)
	public void testQueue() {
		queue.enqueue(50, "High priority");
		queue.enqueue(100, "Highest priority");
		queue.enqueue(0, "Low priority");
		queue.enqueue(25, "Medium priority");
		assertEquals("Highest priority", queue.dequeue());
		assertEquals("High priority", queue.dequeue());
		assertEquals("Medium priority", queue.dequeue());
		assertEquals("Low priority", queue.dequeue());
	}

	@Test (timeout = 100, expected = UnsupportedOperationException.class)
	public void testRemove() {
		queue.enqueue(50, "High priority");
		queue.enqueue(100, "Highest priority");
		queue.enqueue(0, "Low priority");
		queue.enqueue(25, "Medium priority");
		
		Iterator<Entry<Integer, String>> iterator = queue.iterator();
		assertEquals("Highest priority", queue.peek());
		iterator.remove();
		assertEquals("High priority", queue.peek());
	}
	
	@Test (timeout = 100)
	public void testMinMax() {
		IntegerComparator ic = new IntegerComparator();
		ReverseIntegerComparator ric = new ReverseIntegerComparator();
		assertTrue(ic.compare(Integer.MAX_VALUE, Integer.MIN_VALUE) > 0);
		assertTrue(ic.compare(Integer.MIN_VALUE, Integer.MAX_VALUE) < 0);
		assertTrue(ric.compare(Integer.MAX_VALUE, Integer.MIN_VALUE) < 0);
		assertTrue(ric.compare(Integer.MIN_VALUE, Integer.MAX_VALUE) > 0);
	}
}
