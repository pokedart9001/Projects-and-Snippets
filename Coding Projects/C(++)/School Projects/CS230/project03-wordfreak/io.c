#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <ctype.h>
#include <limits.h>
#include "wordtree.h"
#include "io.h"

#define BUF_MAX (INT_MAX/CHAR_BIT + 1000)

void println() {
	check_write(1, "\n", 1);
}

int check_open(char *pathname, int flags) {
	int fd = open(pathname, flags);
	if (fd < 0) {
		fprintf(stderr, "file open failed\n");
		exit(EXIT_FAILURE);
	}
	return fd;
}

int check_close(int fd) {
	int status = close(fd);
	if (status < 0) {
		fprintf(stderr, "file close failed\n");
		exit(EXIT_FAILURE);
	}
	return status;
}

char *itostr(int i, int digits) {
	char *out = malloc(digits + 1);
	*(out + digits) = 0;
	int pos;
	//Fill right side of output with digits of i
	for (pos = digits - 1; i; pos--, i /= 10)
		*(out + pos) = '0' + (i % 10);
	//Fill the rest with spaces
	for (; pos >= 0; pos--)
		*(out + pos) = ' ';
	return out;
}

int check_read(int fd, char *buf, int size) {
	int status = read(fd, buf, size);
	if (status < 0) {
		fprintf(stderr, "buffer read failed\n");
		exit(EXIT_FAILURE);
	}
	return status;
}

int get_byte(int fd) {
	//Sizeable read buffer for reading large chunks of bytes from a file
	//-- static variables keep their value across function calls, so no need for global variables!
	static char read_buffer[BUF_MAX];
	static int offset = BUF_MAX, limit = BUF_MAX;
	
	//Try to reset the buffer if the end is reached
	if (offset == limit) {
		offset = 0;
		limit = check_read(fd, read_buffer, BUF_MAX);
	}
	
	return limit == 0 ? -1 : read_buffer[offset++];
}

int next_word(int fd, char buf[101], int *max_length) {
	for (int i = 0; i < 100; i++) {
		int next_char = get_byte(fd);
		if (!isalpha(next_char)) {
			buf[i] = 0;
			//Keeping track of the maximum word length so far
			if (*max_length < i)
				*max_length = i;
			//Checking for EOF
			if (next_char == -1)
				return 0;
			break;
		}
		buf[i] = next_char;
	}
	return 1;
}

int check_write(int fd, char *buf, int size) {
	int status = write(fd, buf, size);
	if (status < 0) {
		fprintf(stderr, "buffer write failed\n");
		exit(EXIT_FAILURE);
	}
	return status;
}

void print_words(Wordtree *tree, int word_length, int num_digits) {
	if (tree) {
		print_words(get_left(tree), word_length, num_digits);

		//Write current word and determine number of trailing spaces using # of written bytes
		//-- create trailing space string using malloc
		int trail_size = word_length - check_write(1, get_word(tree), word_length);
		char *trail = malloc(trail_size);
		memset(trail, ' ', trail_size);

		//Write trailing spaces, then free the allocated string
		check_write(1, trail, trail_size);
		check_write(1, " : ", 3);
		free(trail);

		//Convert word count to string with leading spaces (using malloc), then print and free count
		char *num = itostr(get_count(tree), num_digits);
		check_write(1, num, num_digits);
		free(num);

		println();
		
		print_words(get_right(tree), word_length, num_digits);
	}
}