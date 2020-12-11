#include <stdio.h>
#include <stdbool.h>
#include <ctype.h>

//                                  -- Board + Logic Methods --

// States whether the position [row, column] is on the board
bool isInBounds(int row, int column) {
	return row >= 0 && row < 6 && column >= 0 && column < 7;
}

// Returns the next row available for the given column
int getRowFromColumn(char board[6][7], int column) {
	for (int row = 5; row >= 0; row--)
		if (board[row][column] == '\0')
			return row;
	return -1;
}

bool checkPiece(char board[6][7], char piece, int row, int column) {
	return isInBounds(row, column) && board[row][column] == piece;
}

// Returns the longest connection of a given piece as a result of being placed in [row, column]
// -- primary function of AI's method of choosing its column
// -- also used to check for win condition
int connected(char board[6][7], char piece, int row, int column) {
	char other = piece == 'X' ? 'O' : 'X';
	if (isInBounds(row, column)) {
		// count[i] corresponds to vertical, diag-up-right, horizontal, & diag-down-right
		int count[4] = {0, 0, 0, 0};
		for (int i = -3; i < 4; i++) {
			// Special case: Act as though piece was placed in [row, column]
			if (i == 0) {
				for (int j = 0; j < 4; j++)
					count[j]++;
			} else {
				// Check for each type of connection w/ piece
				if (checkPiece(board, piece, row - i, column))
					count[0]++;
				if (checkPiece(board, piece, row - i, column + i))
					count[1]++;
				if (checkPiece(board, piece, row, column + i))
					count[2]++;
				if (checkPiece(board, piece, row + i, column + i))
					count[3]++;

				// Check for interruptions of each type of connection by other piece
				if (checkPiece(board, other, row - i, column) && count[0] < 4)
					count[0] = 0;
				if (checkPiece(board, other, row - i, column + i) && count[1] < 4)
					count[1] = 0;
				if (checkPiece(board, other, row, column + i) && count[2] < 4)
					count[2] = 0;
				if (checkPiece(board, other, row + i, column + i) && count[3] < 4)
					count[3] = 0;
			}
		}

		int max = count[0];
		for (int i = 1; i < 4; i++)
			if (count[i] > max)
				max = count[i];
		return max;
	}
	// AI will never try to move to a full column since priority will always be less than that available
	return -1;
}

// Places a piece onto the board at the specified column
// Returns the winning piece (or '\0' if neither piece has won yet)
char move(char board[6][7], char piece, int column) {
	int row = getRowFromColumn(board, column);
	if (row == -1)
		return 'f'; // Special character denoting full (unavailable) column
	board[row][column] = piece;
	if (connected(board, piece, row, column) >= 4)
		return piece;
	return '\0';
}

// Returns the number column that the AI chooses on its turn
int choose(char board[6][7], char pieces[2], int player, int AI, int turn) {
	// Calculate available rows for each column for later use
	int rows[7];
	for (int column = 0; column < 7; column++)
		rows[column] = getRowFromColumn(board, column);

	// Initialize values for best connection & blocking values w/ respective columns
	int bestConnect[2] = {3, connected(board, pieces[AI], rows[3], 3)};
	int bestBlock[2] = {3, connected(board, pieces[player], rows[3], 3)};

	// Check for better connection moves
	for (int i = 1; i < 4; i++) {
		int column = 3 + i;
		int new = connected(board, pieces[AI], rows[column], column);
		if (new > bestConnect[1]) {
			bestConnect[0] = column;
			bestConnect[1] = new;
		}

		column = 3 - i;
		new = connected(board, pieces[AI], rows[column], column);
		if (new > bestConnect[1]) {
			bestConnect[0] = column;
			bestConnect[1] = new;
		}
	}

	// Check for better blocking moves
	for (int i = 1; i < 4; i++) {
		int column = 3 + i;
		int new = connected(board, pieces[player], rows[column], column);
		if (new > bestBlock[1]) {
			bestBlock[0] = column;
			bestBlock[1] = new;
		}

		column = 3 - i;
		new = connected(board, pieces[player], rows[column], column);
		if (new > bestBlock[1]) {
			bestBlock[0] = column;
			bestBlock[1] = new;
		}
	}

	// Note: AI starts at and moves away from the center (column D) when checking both types of moves,
	// resulting in behavior tending towards the center (e.g. moving to column D on the first turn)

	// Determine whether to prioritize connections or blocks depending on turn order
	bool connectGreaterThanBlock;
	switch (turn % 2) {
		case 0: connectGreaterThanBlock = bestConnect[1] >= bestBlock[1]; break;
		case 1: connectGreaterThanBlock = bestConnect[1] > bestBlock[1]; break;
	}

	// Always return best connection move if greater than 4, otherwise return the greater of the two
	return bestConnect[1] >= 4 || connectGreaterThanBlock ? bestConnect[0] : bestBlock[0];
}


//                                   -- Basic I/O Methods --

// Prints the board
void display(char board[6][7]) {
	printf("\n  A B C D E F G\n");
	for (int r = 0; r < 6; r++) {
		printf("%i", 6 - r);
		for (int c = 0; c < 7; c++)
			printf(" %c", board[r][c] == '\0' ? '.' : board[r][c]);
		printf("\n");
	}
	printf("\n");
}

// Converts the given column letter to an int index (used in main)
int indexOf(char cols[7], char letter) {
	for (int i = 0; i < 7; i++)
		if (cols[i] == letter)
			return i;
	return -1;
}

// Prints the record of moves after a given number of turns
void printMoves(char moves[42], int turn) {
	printf("[");
	for (int i = 0; i < turn; i++)
		printf("%c, ", moves[i]);
	printf("%c]\n", moves[turn]);
}

int main(void) {
	char pieces[2] = {'X', 'O'};
	char moves[42]; // There can be a maximum of 42 moves total in a 6*7 grid
	
	// Determine turn order for player (0 or 1)
	int player;
	do {
		printf("Choose a player (1 or 2): ");
		scanf(" %i", &player);
	} while (player != 1 && player != 2);
	player--;

	int AI = 1 - player; // AI either equals 1 (i.e. 1 - 0) or 0 (i.e. 1 - 1)
	printf("Player: %c\nAI: %c\n", pieces[player], pieces[AI]);

	char board[6][7] = {
		{'\0', '\0', '\0', '\0', '\0', '\0', '\0'},
		{'\0', '\0', '\0', '\0', '\0', '\0', '\0'},
		{'\0', '\0', '\0', '\0', '\0', '\0', '\0'},
		{'\0', '\0', '\0', '\0', '\0', '\0', '\0'},
		{'\0', '\0', '\0', '\0', '\0', '\0', '\0'},
		{'\0', '\0', '\0', '\0', '\0', '\0', '\0'},
	};

	char cols[7] = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
	char winner = '\0';

	display(board);

	for (int turn = 0; turn < 42; turn++) { // Pieces correspond to turn order
		char currentPiece = pieces[turn % 2];
		int column;
		
		do {
			if (turn % 2 == player) {
				do {
					char choice[2];
					printf("Choose a column (A-G): ");
					scanf(" %s", choice);
					column = indexOf(cols, toupper(choice[0]));
				} while (column == -1);
			} else if (turn % 2 == AI) {
				printf("Thinking...\n");
				column = choose(board, pieces, player, AI, turn);
			} else {
				return 1;
			}
			winner = move(board, currentPiece, column);
		} while (winner == 'f'); // Repeat until current player chooses an available column

		display(board);
		moves[turn] = cols[column];

		if (winner != '\0') {
			printf("%c wins!\n", winner);
			printMoves(moves, turn);
			return 0;
		}
	}

	printf("Draw\n");
	printMoves(moves, 41);
	return 0;
}