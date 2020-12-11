#include <err.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>

//pre-defined number of operators and number of lines
#define NUM_OPERATORS 3
#define NUM_LINES 5

//global veriable keeps track of thread order
int next_id = 0;

//binary semaphore used to moderate access to connected
sem_t connected_lock;

//counting semaphore used to moderate # of operators in use
sem_t operators;

void *phonecall(void *vargp) {
	//static variable keeps track of # of threads using phone lines
	static int connected = 0;

	//increment next_id and copy to local variable
	//-- does not need to be locked because next_id is only incremented once per thread
	int caller = ++next_id;
	
	//no threads after #240 allowed
	if (caller > 240) {
		printf("Looks like caller #%i is too late. Tough luck...\n", caller);
		return NULL;
	}

	//try to connect, wait until line is available
	//-- locked with binary semaphore — only 1 thread at a time should check/modify connected
	//-- once a line is available, increment connected
	while (1) {
		sem_wait(&connected_lock);
		if (connected == NUM_LINES) {
			printf("Caller #%i is calling, busy signal...\n", caller);
			sem_post(&connected_lock);
			sleep(1);
		} else {
			printf("Caller #%i has an available line! Call ringing...\n", caller);
			connected++;
			break;
		}
	}
	sem_post(&connected_lock);

	//speak with an operator for 3 seconds, then hang up
	//-- locked with counting semaphore — up to 3 threads at a time are able to speak with operators
	sem_wait(&operators);
	printf("Caller #%i is speaking with an operator...\n", caller);
	sleep(3);
	printf("Caller #%i has bought a ticket!\n", caller);
	printf("Caller #%i has hung up.\n", caller);
	sem_post(&operators);

	sem_wait(&connected_lock);
	connected--;
	sem_post(&connected_lock);
	return NULL;
}

int main(int argc, char *argv[]) {
	//get # of callers and prepare storage for thread IDs
	int callers = (argc == 1 ? 1 : atoi(argv[1]));
	pthread_t thread_id[callers];

	//initialize binary semaphore for connected and counting semaphore for operators
	//-- includes error checking
	if (sem_init(&connected_lock, 0, 1)) {
		perror("connected_lock sem_init failed");
		exit(EXIT_FAILURE);
	}
	if (sem_init(&operators, 0, NUM_OPERATORS)) {
		perror("operators sem_init failed");
		exit(EXIT_FAILURE);
	}

	//create same number of threads as number of callers and call phonecall for each
	//-- includes error checking
	for (int i = 0; i < callers; i++) {
		int status = pthread_create(&thread_id[i], NULL, phonecall, NULL);
		if (status)
			err(status, "pthread_create failed");
	}

	//wait for every thread to finish
	for (int i = 0; i < callers; i++)
		pthread_join(thread_id[i], NULL);

	//destroy semaphores
	sem_destroy(&connected_lock);
	sem_destroy(&operators);

	return 0;
}