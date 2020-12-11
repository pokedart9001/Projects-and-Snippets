// PRIVATE BEGIN
#include <stdio.h>
#include <unistd.h>

extern char** environ;
// PRIVATE END

// goal: print the environment variables to the file "env.txt", one per line
// (If envp is NULL, the file should be empty, opening in write mode will do that.)
// example:
//   inputs:
//     envp/environ = {"E1=2","E2=7",NULL}
//   outputs:
//     env.txt as a string would be "E1=2\nE2=7\n"
// example:
//   inputs:
//     envp/environ = {NULL} or NULL
//   outputs:
//     env.txt as a string would be ""
//
// TODO: write and complete the main function

int main()
{
	FILE *write;

	if ((write = fopen("env.txt", "w")) == NULL)
		return 1;

	for (char **p = environ; *p; ++p) {
		fprintf(write, "%s\n", *p);
	}
	
	return 0;
}