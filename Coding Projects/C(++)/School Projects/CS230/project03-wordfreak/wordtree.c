#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <math.h>
#include "wordtree.h"

char *get_word(Wordtree *tree) {
	return tree->word;
}

int get_count(Wordtree *tree) {
	return tree->count;
}

Wordtree *get_left(Wordtree *tree) {
	return tree->left;
}

Wordtree *get_right(Wordtree *tree) {
	return tree->right;
}

void set_word(Wordtree *tree, char *word) {
	tree->word = word;
}

void set_left(Wordtree *tree, Wordtree *left) {
	tree->left = left;
}

void set_right(Wordtree *tree, Wordtree *right) {
	tree->right = right;
}

void add_word(Wordtree **tree, char word[], int *max_digits) {
	//Follows conventions for inserting nodes into a BST
	//-- if the current node is NULL, create a new node
	//-- otherwise, if the word in the current node is equal to
	//   the word being placed in, increment that word's counter
	//-- otherwise, look at either the left or right node
	//   depending on if the word being placed in comes
	//   before or after the current word, respectively
	if (!(*tree)) {
		*tree = words(strcpy(malloc(101), word), NULL, NULL);
	} else if (strcmp(get_word(*tree), word) == 0) {
		++((*tree)->count);
	} else {
		Wordtree **next = strcmp(get_word(*tree), word) > 0 ? &((*tree)->left) : &((*tree)->right);
		add_word(next, word, max_digits);
	}

	//Keeping track of the maximum number of digits so far
	int new_digits = (int)(floor(log10(get_count(*tree))) + 1);
	if (*max_digits < new_digits)
		*max_digits = new_digits;
}

Wordtree *words(char *word, Wordtree *left, Wordtree *right) {
	Wordtree *out = (Wordtree *) malloc(sizeof(Wordtree));
	if (out == NULL) {
		fprintf(stderr, "wordtree malloc failed\n");
		exit(EXIT_FAILURE);
	}

	set_word(out, word);
	out->count = 1;
	set_left(out, left);
	set_right(out, right);
	return out;
}

void free_wordtree(Wordtree **tree) {
	//Free the current word, then the left and right trees (recursively), then the current tree
	if (*tree) {
		free(get_word(*tree));
		free_wordtree(&((*tree)->left));
		free_wordtree(&((*tree)->right));
		free(*tree);
		*tree = NULL;
	}
}