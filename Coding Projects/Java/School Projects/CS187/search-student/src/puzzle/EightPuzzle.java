package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import search.SearchProblem;
import search.Solver;

/**
 * A class to represent an instance of the eight-puzzle.
 * 
 * The spaces in an 8-puzzle are indexed as follows:
 * 
 * 0 | 1 | 2 --+---+--- 3 | 4 | 5 --+---+--- 6 | 7 | 8
 * 
 * The puzzle contains the eight numbers 1-8, and an empty space. If we
 * represent the empty space as 0, then the puzzle is solved when the values in
 * the puzzle are as follows:
 * 
 * 1 | 2 | 3 --+---+--- 4 | 5 | 6 --+---+--- 7 | 8 | 0
 * 
 * That is, when the space at index 0 contains value 1, the space at index 1
 * contains value 2, and so on.
 * 
 * From any given state, you can swap the empty space with a space adjacent to
 * it (that is, above, below, left, or right of it, without wrapping around).
 * 
 * For example, if the empty space is at index 2, you may swap it with the value
 * at index 1 or 5, but not any other index.
 * 
 * Only half of all possible puzzle states are solvable! See:
 * https://en.wikipedia.org/wiki/15_puzzle for details.
 * 
 * 
 * @author liberato
 *
 */
public class EightPuzzle implements SearchProblem<List<Integer>> {

	List<Integer> initialState;
	/**
	 * Creates a new instance of the 8 puzzle with the given starting values.
	 * 
	 * The values are indexed as described above, and should contain exactly the
	 * nine integers from 0 to 8.
	 * 
	 * @param startingValues
	 *            the starting values, 0 -- 8
	 * @throws IllegalArgumentException
	 *             if startingValues is invalid
	 */
	public EightPuzzle(List<Integer> startingValues) {
		if (!isValid(startingValues))
			throw new IllegalArgumentException();
		initialState = startingValues;
	}

	private boolean isValid(List<Integer> state) {
		boolean check = state.size() == 9;
		for (int i = 0; i < 9; i++)
			check = check && state.contains(i);
		return check;
	}

	@Override
	public List<Integer> getInitialState() {
		return initialState;
	}

	@Override
	public List<List<Integer>> getSuccessors(List<Integer> currentState) {
		List<List<Integer>> successors = new ArrayList<List<Integer>>();
		int index = currentState.indexOf(0);

		if (index % 3 != 0) {
			List<Integer> newState = new ArrayList<Integer>(currentState);
			newState.set(index - 1,
					newState.set(index, newState.get(index - 1)));
			successors.add(newState);
		}

		if (index % 3 != 2) {
			List<Integer> newState = new ArrayList<Integer>(currentState);
			newState.set(index + 1,
					newState.set(index, newState.get(index + 1)));
			successors.add(newState);
		}

		if (index > 2) {
			List<Integer> newState = new ArrayList<Integer>(currentState);
			newState.set(index - 3,
					newState.set(index, newState.get(index - 3)));
			successors.add(newState);
		}

		if (index < currentState.size() - 3) {
			List<Integer> newState = new ArrayList<Integer>(currentState);
			newState.set(index + 3,
					newState.set(index, newState.get(index + 3)));
			successors.add(newState);
		}

		return successors;
	}

	@Override
	public boolean isGoal(List<Integer> state) {
		return state.equals(
				Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 0}));
	}

	public static void main(String[] args) {
		EightPuzzle e = new EightPuzzle(
				Arrays.asList(new Integer[]{2, 1, 3, 4, 5, 6, 8, 7, 0}));

		List<List<Integer>> r = new Solver<List<Integer>>(e).solveWithBFS();
		for (List<Integer> l : r) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.print(l.get(3 * i + j) + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}
