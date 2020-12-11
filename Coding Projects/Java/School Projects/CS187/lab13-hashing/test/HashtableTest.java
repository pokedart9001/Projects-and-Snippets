
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

public class HashtableTest {
	int maxSize=5;
	Hashtable linearHash = new Hashtable(maxSize);
	Hashtable compareHash = new Hashtable(maxSize);


	@Test
	public void testput() {
		
		linearHash.put("Florida", "Tallahassee");
		linearHash.put("Massachusetts", "Boston");

		assertEquals(true, linearHash.contains("Massachusetts"));
		assertEquals(2, linearHash.getSize());
	}
	
	@Test
	public void testget() {
		linearHash.put("Florida", "Tallahassee");
		linearHash.put("Massachusetts", "Boston");
		assertEquals("Boston", linearHash.get("Massachusetts"));
		linearHash.put("Maine", "Augusta");
		linearHash.put("Maryland", "Annapolis");
		assertEquals(null, linearHash.get("Annapolis"));
		assertEquals(4, linearHash.getSize());
	}
	
	
	@Test
	public void testrehash() {
		linearHash.put("Florida", "Tallahassee");
		linearHash.put("Massachusetts", "Boston");

		assertEquals("Boston", linearHash.get("Massachusetts"));
		linearHash.put("Maine", "Augusta");
		linearHash.put("Maryland", "Annapolis");
		assertEquals(null, linearHash.get("Annapolis"));
		assertEquals(4, linearHash.getSize());
		
		linearHash.put("New Jersey", "Trenton");
		assertEquals(true, linearHash.isFull());
		linearHash.put("Texas", "Austin");
		linearHash.printHashTable();
		System.out.println((linearHash.getSize()));
		assertEquals("Austin", linearHash.get("Texas"));
		assertEquals("Boston", linearHash.get("Massachusetts"));

		assertEquals(6, linearHash.getSize());
	}
	

	private void testlinearget(int nelements) {
		System.out.print("Test inserting "+nelements+" random elements to hash table...");
		Hashtable table = new Hashtable(nelements*2);	// 50% load factor
		Random rng = new Random();
		long start = System.currentTimeMillis();
		for(int i=0;i<nelements;i++) {
			String key = Integer.toString(rng.nextInt());
			table.put(key, Integer.toString(rng.nextInt()));
		}
		long interval = System.currentTimeMillis()-start;
		System.out.println("Total insertion time: "+interval+", per 1K element cost: "+((float)interval/nelements*1000));
		
		System.out.print("Test inserting "+nelements+" random elements to unsorted array...");
		ArrayList<String> unsorted = new ArrayList<String>(nelements);
		start = System.currentTimeMillis();
		for(int i=0;i<nelements;i++) {
			String key = Integer.toString(rng.nextInt());
			unsorted.add(key);
		}
		long unsorted_interval = System.currentTimeMillis()-start;
		System.out.println("Total insertion time: "+interval+", per 1K element cost: "+((float)interval/nelements*1000));
		
		System.out.println("Search for "+(nelements)+" elements...");
		System.out.print("Hash table total search time: ");
		start = System.currentTimeMillis();
		for(int i=0;i<nelements;i++) {
			table.get(Integer.toString(rng.nextInt()));
		}
		interval = System.currentTimeMillis()-start;
		System.out.print(""+(interval)+", Linear search total time: ");
		start = System.currentTimeMillis();
		for(int i=0;i<nelements;i++) {
			unsorted.contains(Integer.toString(rng.nextInt()));
		}
		unsorted_interval = System.currentTimeMillis()-start;
		System.out.println(unsorted_interval);
		System.out.println("Speedup: "+((float)unsorted_interval/interval));
		System.out.println("-------------");
	}
	@Test 
	public void testlinearget() {
		// A test that checks the hash table insertion time as number of elements scales up
		// ALso compares search time in hash table vs. using linear search (search in unsorted array).
		// Use System.currentTimeMillis() to get the current time in milliseconds and calculate the run time of program.
		
		testlinearget(1000);
		testlinearget(10000);
		testlinearget(20000);

	}
	
	

}
