#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int counts[3] = {0, 0, 0};

static void handler(int sig) {
	switch(sig) {
		case SIGUSR1: counts[0]++; break;
		case SIGUSR2: counts[1]++; break;
		case SIGALRM: counts[2]++; break;
	}
	printf("recieved: %i\n", sig);
}

int main(int argc, char *argv[]) {
	int signals[3] = {SIGUSR1, SIGUSR2, SIGALRM};
	for (int i = 0; i < 3; i++) {
		if (signal(signals[i], handler) == SIG_ERR)
			exit(-1);
		if (fork() == 0) {
			for (int j = 0; j < atoi(argv[i + 1]); j++) {
				kill(getppid(), signals[i]);
				printf("sent: %i\n", signals[i]);
				sleep(1);
			}
			exit(0);
		}
	}
	wait(NULL);
	wait(NULL);
	wait(NULL);
	printf("[SIGUSR1: %i, SIIGUSR2: %i, SIGALRM: %i]\n", counts[0], counts[1], counts[2]);
}