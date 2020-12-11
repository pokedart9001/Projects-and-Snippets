package binary_search;

import java.util.Arrays;
import java.util.Random;

public class RunTests {
	public static void main(String[] args) {
		System.out.println("====== Test BST search and performance ======");
		testBSTSearch();
		
		System.out.println("\n====== Test BST validity ======");
		testValidBST();
	}
	
	public static String searchResult(BST<Integer> bst, Integer elem) {
		return (bst.search(elem)==null)?"it does NOT exist":"it exists";
	}
	
	public static void testValidBST() {
		BST<Integer> bst = new BST<Integer>();
		System.out.println("An empty tree is valid BST, and your answer is: " + bst.isValid());
		
		bst = new BST<Integer>(new BSTNode<Integer>(10));
		System.out.println("A tree with only a root is valid BST, and your answer is: " + bst.isValid());
		
		BSTNode<Integer> test1 = new BSTNode<Integer>(10);
		test1 = new BSTNode<Integer>(12, test1, null);
		test1 = new BSTNode<Integer>(8, null, test1);
		test1 = new BSTNode<Integer>(7, null, test1);
		BSTNode<Integer> left = new BSTNode<Integer>(1);
		BSTNode<Integer> right = new BSTNode<Integer>(5);
		left = new BSTNode<Integer>(2, left, right);
		test1.left = left;
		bst = new BST<Integer>(test1);
		System.out.println("test1.isValid() should be true, and your answer is " + bst.isValid());
		
		BSTNode<Integer> test2 = new BSTNode<Integer>(10);
		test2 = new BSTNode<Integer>(15, test2, null);
		test2 = new BSTNode<Integer>(12, null, test2);
		test2 = new BSTNode<Integer>(6, null, test2);
		left = new BSTNode<Integer>(1);
		right = new BSTNode<Integer>(5);
		left = new BSTNode<Integer>(2, left, right);
		test2.left = left;
		bst = new BST<Integer>(test2);
		System.out.println("test2.isValid() should be false, and your answer is " + bst.isValid());
		
		BSTNode<Integer> test3 = new BSTNode<Integer>(10);
		test3 = new BSTNode<Integer>(12, test3, null);
		test3 = new BSTNode<Integer>(8, null, test3);
		test3 = new BSTNode<Integer>(6, null, test3);
		left = new BSTNode<Integer>(1);
		right = new BSTNode<Integer>(7);
		left = new BSTNode<Integer>(2, left, right);
		test3.left = left;
		bst = new BST<Integer>(test3);
		System.out.println("test3.isValid() should be false, and your answer is " + bst.isValid());
	}
		
	public static void testBSTSearch() {
		// Test BST search
		// Create random number array
		final int N = 1000000;
		Integer[] elements = new Integer[N];
		Integer[] sorted = new Integer[N];
		Random rng = new Random();	// random number generator
		for(int i=0;i<N;i++) {
			elements[i] = rng.nextInt();
			sorted[i] = elements[i];
		}
		
		Arrays.sort(sorted);
		BST<Integer> bst = new BST<Integer>(sorted);
		// Test searching for K numbers
		final int K = 500;
		
		System.out.println("Test search correctness 1");
		for(int i = 0; i < K; i++) {
			Integer target = elements[i];	// any element in the random array should exist
			if(bst.search(target)==null) {
				System.out.println("Failed!!!");
				return;
			}
		}	
		System.out.println("Passed");
		
		System.out.println("Test search correctness 2");
		for(int i = 0; i < K; i++) {
			Integer target = rng.nextInt();
			// test random numbers and consistency between linear search and BST search
			if(linearSearch(elements, target) != (bst.search(target)!=null)) {
				System.out.println("Failed!!!");
				return;
			}
		}
		System.out.println("Passed");
		
		long tStart, tEnd, tDelta;
		double elapsedSeconds;
		
		System.out.println("Linear search for " + K + " numbers started...");
		tStart = System.currentTimeMillis();
		for (int i = 0; i < K; i++) {
			linearSearch(elements, rng.nextInt());
		}
		tEnd = System.currentTimeMillis();
		tDelta = tEnd - tStart;
		elapsedSeconds = tDelta / 1000.0;
		System.out.println("Finished in " + elapsedSeconds + " second.");
		
		System.out.println("BST search for " + K + " numbers started...");
		tStart = System.currentTimeMillis();
		for (int i = 1; i < 1000; i++) {
			bst.search(rng.nextInt());
		}
		tEnd = System.currentTimeMillis();
		tDelta = tEnd - tStart;
		elapsedSeconds = tDelta / 1000.0;
		System.out.println("Finished in " + elapsedSeconds + " second.");
	}
	
	// linear search in an unsorted array
	private static boolean linearSearch(Integer[] array, Integer target) {
		for(int i=0;i<array.length;i++) {
			if(array[i].equals(target)) return true;
		}
		return false;
	}
}
