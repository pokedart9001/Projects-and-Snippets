import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DoublyLinkedTest {
	
	private DoublyLinkedList<String> list;
	private final int LARGE_SIZE = 1000001;
	
	@Before
	public void setup() {
		list = new DoublyLinkedList<String>();
	}

	@Test 
	public void nullIfEven() {
		assertTrue(list.getLength() == 0);		// Make sure setup worked properly
		assertEquals(list.findMiddle(), null);  // 0 element list should return null
		list.add("a");
		list.add("b");
		assertEquals(list.findMiddle(), null);  // Even length list should return null
	}
	
	@Test
	public void testFindMiddleSimple() {
		assertTrue(list.getLength() == 0);			
		list.add("a");
		assertTrue("a".equals(list.findMiddle())); 	// Test 1 length list edge case 
		list.add("b");						
		list.add("c");
		String mid = list.findMiddle();			  	// Test 3 length list - should return "b"	
		list.printList();
		System.out.println("Found mid: " + mid);
		assertTrue("b".equals(list.findMiddle()));
	}
	
	@Test
	public void testAllEqual() {
		DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
		for(int i = 0; i < 10; i++) {
			list.add(1);
		}
		
		list.add(42);
		
		for(int i = 0; i < 10; i++) {
			list.add(1);
		}
		
		Integer mid = list.findMiddle();			  	// Test 3 length list - should return "b"	
		list.printList();
		System.out.println("Found mid: " + mid);
		
		assertTrue(list.findMiddle() == 42);
	}
	
	@Test
	public void testLargeList() {
		DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
		for(int i = 1; i <= LARGE_SIZE; i++) {
			list.add(i);
		}
		Integer mid = list.findMiddle();
		
		assertTrue(mid == (LARGE_SIZE+1)/2);
	}
	
	@Test
	public void testFindMedian() {
		DoublyLinkedList<Float> list = new DoublyLinkedList<Float>();
		
		for(int i = 1; i <= 10; i++) {
			list.add((float)i);
		}
		assertEquals(5.5, DoublyLinkedList.findMedian(list), 0.01);

	}
}
