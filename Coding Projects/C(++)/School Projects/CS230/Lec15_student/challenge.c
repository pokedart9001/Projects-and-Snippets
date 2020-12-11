#include "challenge.h"

// goal: fork the process and have the child execute a process
// param argv: the argument vector for the process to be executed
// assumptions:
//   the first argument of argv is the file name of the executable
//   argv is null terminated
//
// TODO: complete the function
//   fork
//   exec (child), probably most convenient to use execvp
//   have the parent wait on the child
void fork_exec(char** argv)
{
	int pid = fork();
	int status;
	if (pid && argv[1]) {
		execvp(argv[1], argv);
	} else {
		wait(&status);
	}
}