#include "tsh.h"

using namespace std;

void simple_shell::parse_command(char* cmd, char** cmdTokens) {
  // TODO: tokenize the command string into arguments
	*cmdTokens = strtok(cmd, " \n");
	for (int i = 1; (*(cmdTokens + i) = strtok(NULL, " \n")) != NULL; i++);
}

void simple_shell::exec_command(char **argv) {
  // TODO: fork a child process to execute the command.
  // parent process should wait for the child process to complete and reap it
	int rc = fork();
	switch(rc) {
		case 0: execvp(*argv, argv); exit(0);
		default: waitpid(rc, NULL, 0);
	}
}

bool simple_shell::isQuit(char *cmd) {
	return strncmp(cmd, "quit", 4) == 0;
}
