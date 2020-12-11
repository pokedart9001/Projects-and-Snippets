package guessme;

/**
 * A LinkedList-based implementation of the Guess-A-Number game
 */
public class LinkedListGame {
	
	private int guess;
	
	private LLIntegerNode guessesHead, guessesTail, candidatesHead;
	private int numGuesses;
	
	private boolean gameOver;

	
	/********************************************************
	 * NOTE: for this project you must use linked lists
	 * implemented by yourself. You are NOT ALLOWED to use
	 * Java arrays of any type, or any class in the java.util
	 * package (such as ArrayList).
	 *******************************************************/	 
	
	/********************************************************
	 * NOTE: you are allowed to add new methods if necessary,
	 * but DO NOT remove any provided method, and do NOT add
	 * new files (as they will be ignored by the autograder).
	 *******************************************************/
	
	// LinkedListGame constructor method
	public LinkedListGame() {
		reset();
	}
	
	// Resets data members and game state so we can play again
	public void reset() {
		guess = 1000;
		gameOver = false;
		
		guessesHead = null;
		guessesTail = null;
		numGuesses = 0;
		
		candidatesHead = new LLIntegerNode(1000);
		
		LLIntegerNode node = candidatesHead;
		while (node.getInfo() < 9999)
		{
			node.setLink(new LLIntegerNode(node.getInfo() + 1));
			node = node.getLink();
		}
	}
	
	// Returns true if n is a prior guess; false otherwise.
	public boolean isPriorGuess(int n) {
		LLIntegerNode node = guessesHead;
		while (node != null)
		{
			if (node.getInfo() == n)
				return true;
			node = node.getLink();
		}
		return false;
	}
	
	// Returns the number of guesses so far.
	public int numGuesses() {
		return numGuesses;
	}
	
	/**
	 * Returns the number of matches between integers a and b.
	 * You can assume that both are 4-digits long (i.e. between 1000 and 9999).
	 * The return value must be between 0 and 4.
	 * 
	 * A match is the same digit at the same location. For example:
	 *   1234 and 4321 have 0 match;
	 *   1234 and 1114 have 2 matches (1 and 4);
	 *   1000 and 9000 have 3 matches (three 0's).
	 */
	public static int numMatches(int a, int b) {
		int matches = 0;
		
		for (int i = 0; i < 4; i++)
		{
			if (a % 10 == b % 10)
				matches++;
			a /= 10; b /= 10;
		}
		
		return matches;
	}
	
	/**
	 * Returns true if the game is over; false otherwise.
	 * The game is over if the number has been correctly guessed
	 * or if no candidate is left.
	 */
	public boolean isOver() {
		return gameOver;
	}
	
	/**
	 * Returns the guess number and adds it to the list of prior guesses.
	 * The insertion should occur at the end of the prior guesses list,
	 * so that the order of the nodes follow the order of prior guesses.
	 */	
	public int getGuess() {
		if (numGuesses == 0)
		{
			guessesHead = new LLIntegerNode(guess);
			guessesTail = guessesHead;
		}
		else
		{
			guessesTail.setLink(new LLIntegerNode(guess));
			guessesTail = guessesTail.getLink();
		}
		numGuesses++;
		return guess;
	}
	
	/**
	 * Updates guess based on the number of matches of the previous guess.
	 * If nmatches is 4, the previous guess is correct and the game is over.
	 * Check project description for implementation details.
	 * 
	 * Returns true if the update has no error; false if no candidate 
	 * is left (indicating a state of error);
	 */
	public boolean updateGuess(int nmatches) {
		if (nmatches == 4)
		{
			gameOver = true;
			return true;
		}
		
		LLIntegerNode newCandidatesHead = null;
		
		LLIntegerNode node = candidatesHead;
		LLIntegerNode newNode = null;
		while (node != null)
		{
			if (numMatches(guess, node.getInfo()) == nmatches)
			{
				if (newCandidatesHead == null)
				{
					newCandidatesHead = new LLIntegerNode(node.getInfo());
					newNode = newCandidatesHead;
				}
				else
				{
					newNode.setLink(new LLIntegerNode(node.getInfo()));
					newNode = newNode.getLink();
				}
			}
			node = node.getLink();
		}
		
		candidatesHead = newCandidatesHead;
		if (candidatesHead != null) guess = candidatesHead.getInfo();
		return candidatesHead != null;
	}
	
	// Returns the head of the prior guesses list.
	// Returns null if there hasn't been any prior guess
	public LLIntegerNode priorGuesses() {
		return guessesHead;
	}
	
	/**
	 * Returns the list of prior guesses as a String. For example,
	 * if the prior guesses are 1000, 2111, 3222, in that order,
	 * the returned string should be "1000, 2111, 3222", in the same order,
	 * with every two numbers separated by a comma and space, except the
	 * last number (which should not be followed by either comma or space).
	 *
	 * Returns an empty string if here hasn't been any prior guess
	 */
	public String priorGuessesString() {
		if (guessesHead == null) return "";
		
		String result = "";
		LLIntegerNode node = guessesHead;
		while (node != null)
		{
			result += node.getInfo() + (node.getLink() == null ? "" : ", ");
			node = node.getLink();
		}
		return result;
	}
}
