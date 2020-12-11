package search;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * An implementation of a Searcher that performs an iterative search, storing
 * the list of next states in a Stack. This results in a depth-first search.
 * 
 */
public class StackBasedDepthFirstSearcher<T> extends Searcher<T> {

	public StackBasedDepthFirstSearcher(SearchProblem<T> searchProblem) {
		super(searchProblem);
	}

	@Override
	public List<T> findSolution() {
		if (solution != null)
			return solution;
		Stack<T> states = new Stack<T>();
		states.push(searchProblem.getInitialState());
		visited.add(states.peek());

		while (!states.isEmpty() && !searchProblem.isGoal(states.peek())) {
			T currentState = states.peek();
			boolean hasNeighbors = false;
			for (T successor : searchProblem.getSuccessors(currentState)) {
				if (!visited.contains(successor)) {
					hasNeighbors = true;
					states.push(successor);
					visited.add(successor);
					break;
				} else {
					continue;
				}
			}
			if (!hasNeighbors)
				states.pop();
		}
		
		List<T> path = new ArrayList<T>();
		for (T state : states)
			path.add(state);
		solution = path;
		return path;
	}
}
