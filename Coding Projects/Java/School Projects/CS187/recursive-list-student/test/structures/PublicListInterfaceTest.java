package structures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PublicListInterfaceTest {

	private ListInterface<String> list;

	@Before
	public void setup(){
          list = new RecursiveList<String>();
	}
	
	private void addFiveItems(ListInterface<String> list) {
		list.insertLast("foo");
		list.insertLast("bar");
		list.insertLast("hello");
		list.insertLast("sky");
		list.insertLast("box");
	}

	@Test (timeout = 500)
	public void testInsertFirstIsEmptySizeAndGetFirst1() {
		assertTrue("Newly constructed list should be empty.", list.isEmpty());
		assertEquals("Newly constructed list should be size 0.", 0, list.size());
		assertEquals("Insert First should return instance of self", list, list.insertFirst("hello"));
		assertFalse("List should now have elements.", list.isEmpty());
		assertEquals("List should now have 1 element.", 1, list.size());
		assertEquals("First element should .equals \"hello\".", "hello", list.getFirst());
		list.insertFirst("world");
		assertEquals(2, list.size());
		list.insertFirst("foo");
		assertEquals(3, list.size());
		assertEquals("First element should .equals \"foo\".", "foo", list.getFirst());
	}
	
	@Test (timeout = 500)
	public void testRemoveAndIndexOf() {
		addFiveItems(list);
		assertEquals(5, list.size());
		assertEquals("foo", list.removeFirst());
		assertEquals(4, list.size());
		assertEquals("box", list.removeLast());
		assertEquals(3, list.size());
		assertEquals(1, list.indexOf("hello"));
		assertEquals("hello", list.removeAt(1));
		assertEquals(2, list.size());
		assertEquals(-1, list.indexOf("hello"));
		assertEquals("bar", list.removeFirst());
		assertEquals(1, list.size());
		assertEquals("sky", list.removeLast());
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
	}
	
	@Test (timeout = 500)
	public void testExceptionOutOfBounds() {
		System.out.println(list.removeAt(1));
	}
}
