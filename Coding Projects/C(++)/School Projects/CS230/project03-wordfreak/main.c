#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include "wordtree.h"
#include "io.h"

//Wrapper function for generating the wordtree from a given file
void scan_file(int fd, Wordtree **words, int *max_word_length, int *max_num_digits) {
	char next[101];
	while (next_word(fd, next, max_word_length)) {
		if (next[0])
			add_word(words, next, max_num_digits);
	}
}

int main(int argc, char *argv[]) {
	Wordtree *words = NULL; //empty wordtree
	println();

	int max_word_length = 0, max_num_digits = 0; //To determine buffer sizes for printing the words
	switch(argc) {
		//If argc is 1, read from either WORD_FREAK's designated file or stdin
		case 1:;
			char *freak = getenv("WORD_FREAK");
			int fd = freak ? check_open(freak, O_RDONLY) : 0;
			scan_file(fd, &words, &max_word_length, &max_num_digits);
			if (freak)
				check_close(fd);
			break;
		//Otherwise, read from the files specified in the command line args
		default:;
			for (int i = 1; argv[i]; i++) {
				int fd = check_open(argv[i], O_RDONLY);
				scan_file(fd, &words, &max_word_length, &max_num_digits);
				check_close(fd);
			}
			break;
		//Apparently I needed to put a semicolon after case so that char *freak didn't cause an error.
	}

	print_words(words, max_word_length, max_num_digits);
	println();

	free_wordtree(&words);
	return 0;
}