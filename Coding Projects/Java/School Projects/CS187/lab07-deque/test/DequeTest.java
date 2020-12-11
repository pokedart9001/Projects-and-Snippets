import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class DequeTest {
	
	Deque<String> deque;
	
	@Before
	public void setup() {
		deque = new LinkedDeque<String>();
	}

	@Test
	public void testFrontMethods() {
		assertTrue(deque.isEmpty()); // originally the deque should be empty
		deque.addToFront("Bob"); // add Bob to the front of the deque
		assertFalse(deque.isEmpty()); // deque should no longer be empty
		assertEquals("Bob", deque.first());
		deque.addToFront("VIP Alice"); // add VIP Alice to the front of the deque
		assertEquals("VIP Alice", deque.first());
		assertEquals("VIP Alice", deque.removeFront());
		assertFalse(deque.isEmpty());
		assertEquals("Bob", deque.first()); // Bob should still be in the deque
		deque.removeFront();
		assertTrue(deque.isEmpty());
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testRearMethods() {
		assertTrue(deque.isEmpty());
		deque.addToRear("Bob");
		assertFalse(deque.isEmpty());
		assertEquals("Bob", deque.last());
		deque.addToRear("VIP Alice");
		assertEquals("VIP Alice", deque.last());
		assertEquals("VIP Alice", deque.removeRear());
		assertFalse(deque.isEmpty());
		assertEquals("Bob", deque.last());
		deque.removeRear();
		assertTrue(deque.isEmpty());
		deque.removeRear();
		
		/*
		 * Obviously, this is nearly identical to testFrontMethods().
		 * The nice thing about supporting both front and rear operations
		 * is that you can just treat rear operations like front operations
		 * with ‘next’ and ‘prev’ swapped and ‘front’ and ‘rear’ swapped.
		 */
	}
}
