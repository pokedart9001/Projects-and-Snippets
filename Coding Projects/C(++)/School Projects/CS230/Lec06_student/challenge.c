#include "challenge.h"

// goal: copy nlines lines from rfile to wfile
// param rfile: name of file to read from
// param wfile: name of file to write to
// param nlines: number lines to copy
// return: error code
//   0 if no issues
//   -1 if error in opening or closing file
//
// TODO: complete the function
//   1. open files. do NOT open wfile in append mode (don't forget error checking)
//   2. copy the n lines (beware nline > number of lines in rfile)
//   3. close files
int copy(const char* rfile, const char* wfile, int nlines)
{
	FILE *read, *write;
	int c;

	if ((read = fopen(rfile, "r")) == NULL || (write = fopen(wfile, "w")) == NULL)
		return -1;
	for (int i = 0; i < nlines; i++) {
		do {
			putc(c = getc(read), write);
		} while (!feof(read) && c != '\n');
	}
	if (fclose(read) || fclose(write))
		return -1;
	return 0;
}