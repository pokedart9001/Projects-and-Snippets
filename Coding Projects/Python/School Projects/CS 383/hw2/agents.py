import random
import math


BOT_NAME = "N.GAGE"


class RandomAgent:
	"""Agent that picks a random available move.  You should be able to beat it."""
	def get_move(self, state, depth=None):
		return random.choice(state.successors())


class HumanAgent:
	"""Prompts user to supply a valid move."""
	def get_move(self, state, depth=None):
		move__state = dict(state.successors())
		prompt = "Kindly enter your move {}: ".format(sorted(move__state.keys()))
		move = None
		while move not in move__state:
			try:
				move = int(input(prompt))
			except ValueError:
				continue
		return move, move__state[move]


class MinimaxAgent:
	"""Artificially intelligent agent that uses minimax to optimally select the best move."""

	def get_move(self, state, depth=None):
		"""Select the best available move, based on minimax value."""
		nextp = state.next_player()
		best_util = -math.inf if nextp == 1 else math.inf
		best_move = None
		best_state = None

		for move, state in state.successors():
			util = self.minimax(state, depth)
			if ((nextp == 1) and (util > best_util)) or ((nextp == -1) and (util < best_util)):
				best_util, best_move, best_state = util, move, state
		return best_move, best_state

	def minimax(self, state, depth):
		"""Determine the minimax utility value of the given state."""
		if state.is_full():
			return state.score()

		optimize = max if state.next_player() == 1 else min
		return optimize(self.minimax(succ, depth) for (move, succ) in state.successors())


class HeuristicAgent(MinimaxAgent):
	"""Artificially intelligent agent that uses depth-limited minimax to select the best move."""

	def minimax(self, state, depth):
		return self.minimax_depth(state, depth)

	def minimax_depth(self, state, depth):
		"""Determine the heuristically estimated minimax utility value of the given state.

		Args:
			state: a connect383.GameState object representing the current board
			depth: the maximum depth of the game tree that minimax should traverse before
				estimating the utility using the evaluation() function.  If depth is 0, no
				traversal is performed, and minimax returns the results of a call to evaluation().
				If depth is None, the entire game tree is traversed.

		Returns: the minimax utility value of the state
		"""
		if state.is_full():
			return state.score()
		if depth == 0:
			return self.evaluation(state)
		
		optimize = max if state.next_player() == 1 else min
		next_depth = depth if depth == None else depth - 1
		return optimize(self.minimax_depth(succ, next_depth) for (move, succ) in state.successors())

	def evaluation(self, state):
		"""Estimate the utility value of the game state based on features.

		N.B.: This method must run in O(1) time!

		Args:
			state: a connect383.GameState object representing the current board

		Returns: a heuristic estimate of the utility value of the state
		Note: This is a slightly modified version of score() which counts blank spaces as both p1 and p2 at the same time.
		"""
		p1_score = 0
		p2_score = 0
		for run in state.get_all_rows() + state.get_all_cols() + state.get_all_diags():
			for elt, length in self.streaks_with_blanks(run):
				if (elt == 1) and (length >= 3):
					p1_score += length**2
				elif (elt == -1) and (length >= 3):
					p2_score += length**2
		return p1_score - p2_score

	def streaks_with_blanks(self, lst):
		"""
		Return the lengths of all the streaks of the same element in a sequence.
		Modified version of streaks(lst) such that blank spaces are counted as part of a streak.
		"""
		rets = []  # list of (element, length) tuples
		curr_len_p1 = 1 if lst[0] == 1 or lst[0] == 0 else 0
		curr_len_p2 = 1 if lst[0] == -1 or lst[0] == 0 else 0

		for curr in lst[1:]:
			if curr == 1:
				if curr_len_p2 > 0:
					rets.append((-1, curr_len_p2))
				curr_len_p2 = 0
				curr_len_p1 += 1
			elif curr == -1:
				if curr_len_p1 > 0:
					rets.append((1, curr_len_p1))
				curr_len_p1 = 0
				curr_len_p2 += 1
			else:
				curr_len_p1 += 1
				curr_len_p2 += 1

		if curr_len_p1 > 0:
			rets.append((1, curr_len_p1))
		if curr_len_p2 > 0:
			rets.append((-1, curr_len_p2))
		return rets


class PruneAgent(HeuristicAgent):
	"""Smarter computer agent that uses minimax with alpha-beta pruning to select the best move."""

	def minimax(self, state, depth):
		return self.minimax_prune(state, depth)

	def minimax_prune(self, state, depth):
		"""Determine the minimax utility value the given state using alpha-beta pruning.

		The value should be equal to the one determined by ComputerAgent.minimax(), but the 
		algorithm should do less work.  You can check this by inspecting the class variables
		GameState.p1_state_count and GameState.p2_state_count, which keep track of how many
		GameState objects were created over time.

		N.B.: When exploring the game tree and expanding nodes, you must consider the child nodes
		in the order that they are returned by GameState.successors().  That is, you cannot prune
		the state reached by moving to column 4 before you've explored the state reached by a move
		to to column 1.

		Args: see ComputerDepthLimitAgent.minimax() above

		Returns: the minimax utility value of the state
		"""
		value = self.max_value if state.next_player() == 1 else self.min_value
		return value(state, -math.inf, math.inf, depth)

	def max_value(self, state, alpha, beta, depth):
		if state.is_full():
			return state.score()
		if depth == 0:
			return self.evaluation(state)
		next_depth = depth if depth == None else depth - 1

		val = -math.inf
		for (move, succ) in state.successors():
			val = max(val, self.min_value(succ, alpha, beta, next_depth))
			if val >= beta:
				return val
			alpha = max(alpha, val)
		return val

	def min_value(self, state, alpha, beta, depth):
		if state.is_full():
			return state.score()
		if depth == 0:
			return self.evaluation(state)
		next_depth = depth if depth == None else depth - 1

		val = math.inf
		for (move, succ) in state.successors():
			val = min(val, self.max_value(succ, alpha, beta, next_depth))
			if val <= alpha:
				return val
			beta = min(beta, val)
		return val