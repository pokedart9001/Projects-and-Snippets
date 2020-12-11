//Definition (BST)
typedef struct wordtree {
	char *word;
	int count;
	struct wordtree *left;
	struct wordtree *right;
} Wordtree;

//Getters
char *get_word(Wordtree *tree);

int get_count(Wordtree *tree);

Wordtree *get_left(Wordtree *tree);

Wordtree *get_right(Wordtree *tree);

//Setters
void set_word(Wordtree *tree, char *word);

void set_left(Wordtree *tree, Wordtree *left);

void set_right(Wordtree *tree, Wordtree *right);

//Functions

//Increases the count of a word in the tree if present, otherwise adds the word to the tree
//-- Tree is sorted by ASCII order of words
//-- outputs new maximum number of digits to max_digits, when greater than max_digits
void add_word(Wordtree **tree, char word[], int *max_digits);

//Constructor
Wordtree *words(char *word, Wordtree *left, Wordtree *right);

//Destructor
void free_wordtree(Wordtree **tree);