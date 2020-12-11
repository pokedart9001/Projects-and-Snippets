package guessme;

/**
 * An Array-based implementation of the Guess-A-Number game
 */
public class ArrayGame {

	// stores the next number to guess
	private int guess;
	
	// stores the previous guesses
	private int[] prevGuesses;
	
	// stores eliminated guesses
	private boolean[] eliminated;
	
	// stores the "game over" flag
	private boolean gameOver;
	
	// TODO: declare additional data members, such as arrays that store
	// prior guesses, eliminated candidates etc.

	// NOTE: only primitive type arrays are allowed, such as int[], boolean[] etc.
	// You MAY NOT use any Collection type (such as ArrayList) provided by Java.
	
	/********************************************************
	 * NOTE: you are allowed to add new methods if necessary,
	 * but DO NOT remove any provided method, otherwise your
	 * code will fail the JUnit tests!
	 * Also DO NOT create any new Java files, as they will
	 * be ignored by the autograder!
	 *******************************************************/
	
	// ArrayGame constructor method
	public ArrayGame() {
		reset();
	}
	
	// Resets data members and game state so we can play again
	public void reset() {
		guess = 1000;
		prevGuesses = null;
		eliminated = new boolean[9000];
		gameOver = false;
	}
	
	// Returns true if n is a prior guess; false otherwise.
	public boolean isPriorGuess(int n) {
		for (int i : prevGuesses)
			if (i == n)
				return true;
		return false;
		
	}
	
	// Returns the number of guesses so far.
	public int numGuesses() {
		return prevGuesses == null ? 0 : prevGuesses.length;
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
	public static int numMatches(int a, int b) { // DO NOT remove the static qualifier
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
	 * Appends an integer to the end of the given integer array
	 * Increases the length of the array by 1
	 */
	public static int[] append(int[] arr, int item) {
		int[] result = new int[arr.length + 1];
		for (int i = 0; i < arr.length; i++)
			result[i] = arr[i];
		result[arr.length] = item;
		return result;
	}
	
	/**
	 * Returns true if the game is over; false otherwise.
	 * The game is over if the number has been correctly guessed
	 * or if all candidates have been eliminated.
	 */
	public boolean isOver() {
		return gameOver;
	}
	
	// Returns the guess number and adds it to the list of prior guesses.
	public int getGuess() {
		if (prevGuesses == null) prevGuesses = new int[0];
		prevGuesses = append(prevGuesses, guess);
		return guess;
	}
	
	/**
	 * Updates guess based on the number of matches of the previous guess.
	 * If nmatches is 4, the previous guess is correct and the game is over.
	 * Check project description for implementation details.
	 * 
	 * Returns true if the update has no error; false if all candidates
	 * have been eliminated (indicating a state of error);
	 */
	public boolean updateGuess(int nmatches) {
		int nextGuess = 0;
		boolean allEliminated = true;
		
		for (int i = 0; i < eliminated.length; i++)
		{
			if (!eliminated[i])
			{
				allEliminated = false;
				eliminated[i] = numMatches(guess, i + 1000) != nmatches;
				if (!eliminated[i] && nextGuess == 0)
					nextGuess = i + 1000;
			}
		}
		if (nextGuess == 0) return false;
		guess = nextGuess;
		gameOver = nmatches == 4 || allEliminated;
		return !allEliminated;
	}
	
	// Returns the list of guesses so far as an integer array.
	// The size of the array must be the number of prior guesses.
	// Returns null if there has been no prior guess
	public int[] priorGuesses() {
		return prevGuesses;
	}
}
