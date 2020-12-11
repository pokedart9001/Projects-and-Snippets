board = [ \
['-', '-', '-', '-', '+'], \
['-', '-', '-', '-', '+'], \
['-', '-', '-', '-', '+'], \
['-', '-', '-', '+', '+'], \
['-', '-', '-', '-', '+']]

def neighbors(x, y):
	global board
	count = 0
	for i in range(x-1, x+2):
		for j in range(y-1, y+2):
			if not (i == x and j == y):
				a = i
				if a < 0:
					a += 5
				elif a > 4:
					a -= 5
				b = j
				if b < 0:
					b += 5
				elif b > 4:
					b -= 5
				if board[a][b] == '+':
					count += 1
	return count

def update():
	global board
	result = [[],[],[],[],[]]
	for i in range(5):
		for j in range(5):
			n = neighbors(i, j)
			if board[i][j] == '+' and (n < 2 or n > 3):
				result[i].append('-')
			elif board[i][j] == '-' and n == 3:
				result[i].append('+')
			else:
				result[i].append(board[i][j])
	board = result

def print_board():
	global board
	for i in range(5):
		row = ''
		for j in range(5):
			row += board[i][j] + ('' if j == 5 else ' ')
		print(row)

while True:
	print_board()
	if input() == 'end':
		break
	update()