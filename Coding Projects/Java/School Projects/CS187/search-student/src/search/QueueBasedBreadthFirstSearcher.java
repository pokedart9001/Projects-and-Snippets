package search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An implementation of a Searcher that performs an iterative search, storing
 * the list of next states in a Queue. This results in a breadth-first search.
 * 
 */
public class QueueBasedBreadthFirstSearcher<T> extends Searcher<T> {

	public QueueBasedBreadthFirstSearcher(SearchProblem<T> searchProblem) {
		super(searchProblem);
	}

	@Override
	public List<T> findSolution() {
		if (solution != null)
			return solution;
		Queue<T> states = new LinkedList<T>();
		states.add(searchProblem.getInitialState());
		visited.add(states.peek());

		List<T> predecessors = new ArrayList<T>();
		predecessors.add(null);

		while (!states.isEmpty() && !searchProblem.isGoal(states.peek())) {
			T currentState = states.remove();
			for (T successor : searchProblem.getSuccessors(currentState)) {
				if (!visited.contains(successor)) {
					states.add(successor);
					visited.add(successor);
					predecessors.add(currentState);
				}
			}
		}

		List<T> path = new ArrayList<T>();
		if (states.isEmpty())
			return path;
		for (T state = states.peek(); state != null; state = predecessors
				.get(visited.indexOf(state)))
			path.add(0, state);
		solution = path;
		return path;
	}
}
