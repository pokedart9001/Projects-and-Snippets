#include "BoundedBuffer.h"

BoundedBuffer::BoundedBuffer(int N) {
  //TODO: constructor to initiliaze all the variables declared in BoundedBuffer.h
	buffer = (int*)calloc(N, sizeof(int));
	buffer_size = N;
	buffer_cnt = 0;
	buffer_first = -1;
	buffer_last = -1;
}

BoundedBuffer::~BoundedBuffer() {
  //TODO: destructor to clean up anything necessary
	free(buffer);

	pthread_mutex_destroy(&buffer_lock);
	pthread_cond_destroy(&buffer_not_full);
	pthread_cond_destroy(&buffer_not_empty);
}

void BoundedBuffer::append(int data) {
  //TODO: append a data item to the circular buffer
	pthread_mutex_lock(&buffer_lock);
	while (buffer_cnt == buffer_size)
		pthread_cond_wait(&buffer_not_full, &buffer_lock);

	buffer_last = (buffer_last + 1) % buffer_size;
	buffer[buffer_last] = data;
	buffer_cnt++;

	pthread_cond_signal(&buffer_not_empty);
	pthread_mutex_unlock(&buffer_lock);
}

int BoundedBuffer::remove() {
  //TODO: remove and return a data item from the circular buffer
	pthread_mutex_lock(&buffer_lock);
	while (isEmpty())
		pthread_cond_wait(&buffer_not_empty, &buffer_lock);

	buffer_first = (buffer_first + 1) % buffer_size;
	int out = buffer[buffer_first];
	buffer_cnt--;

	pthread_cond_signal(&buffer_not_full);
	pthread_mutex_unlock(&buffer_lock);
	return out;
}

bool BoundedBuffer::isEmpty() {
  //TODO: check is the buffer is empty
	return buffer_cnt == 0;
}
