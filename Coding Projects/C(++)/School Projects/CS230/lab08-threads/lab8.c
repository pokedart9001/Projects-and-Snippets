#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

// Thread routine prototype.
void *thread(void *vargp);

int main(int argc, char **argv) {
	// We are creating two threads, so we create two thread ids.
	int n = (argc == 1 ? 2 : atoi(argv[1]));
	pthread_t tid[n];

	printf("Hello from the main thread.\n");

	// Create two threads and save their IDs.
	for (int i = 0; i < n; i++) {
		printf("Creating thread %i.\n", i + 1);
		pthread_create(&tid[i], NULL, thread, NULL);
	}

	// Wait and join two threads.
	printf("Main thread is going to wait on peer threads.\n");
	for (int i = 0; i < n; i++)
		pthread_join(tid[i], NULL);
	printf("Peer threads have completed.\n");

	return EXIT_SUCCESS;
}

void *thread(void *vargp) {
	// This thread gets the thread ID of the calling thread. Unlike the
	// process ID of a process, the thread ID is only unique to the
	// process that created it.
	pthread_t tid = pthread_self();
	printf("Hello from thread %u.\n", (unsigned int)tid);
	return NULL;
}