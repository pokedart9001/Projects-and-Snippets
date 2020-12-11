//Pretty self-explainatory.
void println();

//Performs an error check while executing open(pathname, flags)
//-- returns file descriptor from open(), if non-negative
int check_open(char *pathname, int flags);

//Performs an error check while executing close(fd)
//-- returns same status as close(), if non-negative
int check_close(int fd);

//Returns an allocated string (char *) equivalent to the given integer
//-- no need to account for non-positive numbers, as they will never appear in a word tree
char *itostr(int i, int size);

//Performs an error check while executing read(fd, buf, size)
//-- returns same status as read(), if non-negative
int check_read(int fd, char *buf, int size);

//Returns the next byte (character) of the read buffer (a very large char[])
//-- returns -1 if EOF is reached
int get_byte(int fd);

//Reads the next word from the input given by file descriptor fd
//-- returns end-of-file status (1 if EOF reached, otherwise 0)
//-- outputs new maximum word length to max_length, if larger than max_length
int next_word(int fd, char buf[101], int *max_length);

//Performs an error check while executing write(fd, buf, size)
//-- returns same status as write(), if non-negative
int check_write(int fd, char *buf, int size);

//Outputs the formatted tree to stdout using dynamically-sized buffers
//-- prints using in-order traversal (left tree, then current node, then right tree)
void print_words(Wordtree *words, int word_length, int num_digits);