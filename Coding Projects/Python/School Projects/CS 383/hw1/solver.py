import matplotlib.pyplot as plt

from pdqpq import PriorityQueue
from puzz import EightPuzzleBoard
from random import choice

from sys import argv

def manhattan_dist(i, current, goal):
	current_pos = current.find(str(i))
	goal_pos = goal.find(str(i))

	x_dist = abs(current_pos[0] - goal_pos[0])
	y_dist = abs(current_pos[1] - goal_pos[1])

	return (x_dist + y_dist)

def manhattan_sum(current, goal, weighted):
	return sum([(i**2 if weighted else 1) * manhattan_dist(i, current, goal) for i in range(9)])

def priority(state, goal, current_cost, cost, heuristic, mode, weighted):
	priority = 0
	if mode != 'bfs':
		priority += current_cost
		if mode == 'ucost' or mode == 'astar':
			priority += cost
		if mode == 'greedy' or mode == 'astar':
			priority += heuristic(state, goal, weighted)
	return priority

def find_cost(board, transition):
	zero_pos = board.find('0')
	directions = {'up': (zero_pos[0] - 1, zero_pos[1]), 'down': (zero_pos[0] + 1, zero_pos[1]), 'left': (zero_pos[0], zero_pos[1] - 1), 'right': (zero_pos[0], zero_pos[1] + 1)}
	return int(board.get(directions[transition][0], directions[transition][1]))**2

def search(start_state, goal_state, heuristic, mode, weighted):
	frontier = PriorityQueue()
	frontier_count = 0

	explored = set()

	frontier.add((start_state, 0, 0, f'0\tstart\t{start_state}'))
	while not frontier.empty():
		(current_state, current_cost, num_moves, path) = frontier.pop()
		
		if current_state == goal_state:
			return (path, current_cost, len(explored), frontier_count)

		explored.add(current_state)
		
		for (transition, succ) in current_state.successors().items():
			if succ not in explored:
				cost = find_cost(succ, transition) if weighted else 1
				frontier.add((succ, current_cost + cost, num_moves + 1, f'{path}\n{num_moves + 1}\t{transition}\t{succ}'), priority(succ, goal_state, current_cost, cost, heuristic, mode, weighted))
				frontier_count += 1
	return ('no solution', None, len(explored), frontier_count)


def main(mode, start, weighted=True):
	(solution_path, path_cost, expanded_nodes, frontier_size) = search(EightPuzzleBoard(start), EightPuzzleBoard("012345678"), manhattan_sum, mode, weighted)
	print(solution_path)
	if path_cost != None:
		print(f"path cost: {path_cost}")
	print(f"frontier: {frontier_size}")
	print(f"expanded: {expanded_nodes}")

if __name__ == '__main__':
	weighted = len(argv) <= 3 or argv[3] != '--noweight'
	main(argv[1], argv[2], weighted)

# test_cases = ['431657082', '726384015', '620418753', '283605174', '310847652', '058246731', '031478652', '265703481', '472608315', '437521680', '640128753', '267401385', '631452087', '142503687', '180327456', '546108732', '267413085', '531682074', '847132560', '314805672', '035641782', '170852364', '243875160', '430715268', '431607852', '627453810', '425168730', '268401357', '250613748', '056234718', '032715468', '851324670', '431702685', '235174068', '267835014', '145362780', '432761085', '420631785', '016432758', '247813650', '047823165', '158234067', '350672148', '715246083', '075231468', '682301475', '025173648', '170632458', '240365718', '615207834']
# goal = EightPuzzleBoard('012345678')

# avg_cost = {'bfs': 0, 'ucost': 0, 'greedy': 0, 'astar': 0}
# avg_frontier_size = {'bfs': 0, 'ucost': 0, 'greedy': 0, 'astar': 0}
# avg_expanded_nodes = {'bfs': 0, 'ucost': 0, 'greedy': 0, 'astar': 0}

# for mode in ['bfs', 'ucost', 'greedy', 'astar']:
# 	for start in list(map(EightPuzzleBoard, test_cases)):
# 		(_, path_cost, expanded_nodes, frontier_size) = search(start, goal, manhattan_sum, mode, True)
# 		avg_cost[mode] += path_cost
# 		avg_frontier_size[mode] += frontier_size
# 		avg_expanded_nodes[mode] += expanded_nodes
# 	avg_cost[mode] /= len(test_cases)
# 	avg_frontier_size[mode] /= len(test_cases)
# 	avg_expanded_nodes[mode] /= len(test_cases)

# modes = ['BFS', 'UCS', 'Greedy', 'A*']

# fig = plt.figure(figsize = (7, 5))

# plt.bar(modes, avg_cost.values(), color = 'red', width = 0.4)
# plt.xlabel("Search Strategy")
# plt.ylabel("Cost")
# plt.title("Average Path Cost")
# plt.show()

# plt.bar(modes, avg_frontier_size.values(), color = 'red', width = 0.4)
# plt.xlabel("Search Strategy")
# plt.ylabel("Size")
# plt.title("Average Frontier Size")
# plt.show()

# plt.bar(modes, avg_expanded_nodes.values(), color = 'red', width = 0.4)
# plt.xlabel("Search Strategy")
# plt.ylabel("Nodes")
# plt.title("Average Number of Expanded Nodes")
# plt.show()