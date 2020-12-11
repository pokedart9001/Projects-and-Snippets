#include "ProducerConsumer.h"
#include "BoundedBuffer.h"
// TODO: add BoundedBuffer, locks and any global variables here
int num_items;

int produced = 0;
pthread_mutex_t produced_lock;

int consumed = 0;
pthread_mutex_t consumed_lock;

int producer_id = 0;
pthread_mutex_t producer_id_lock;

int consumer_id = 0;
pthread_mutex_t consumer_id_lock;

chrono::milliseconds elapsed(0);
pthread_mutex_t elapsed_lock;

ofstream fileout;
pthread_mutex_t output_lock;

chrono::milliseconds p_timestep;
chrono::milliseconds c_timestep;

BoundedBuffer buffer(5);

void InitProducerConsumer(int p, int c, int psleep, int csleep, int items) {
  //TODO: constructor to initialize variables declared
  //also see instruction for implementation
	num_items = items;

	p_timestep = chrono::milliseconds(psleep);
	c_timestep = chrono::milliseconds(csleep);

	fileout.open("output.txt", ios::trunc);

	pthread_t pids[p];
	for (int i = 0; i < p; i++)
		pthread_create(&pids[i], NULL, producer, NULL);

	pthread_t cids[c];
	for (int i = 0; i < c; i++)
		pthread_create(&cids[i], NULL, consumer, NULL);

	for (pthread_t pid : pids)
		pthread_join(pid, NULL);

	for (pthread_t cid : cids)
		pthread_join(cid, NULL);

	pthread_mutex_destroy(&produced_lock);
	pthread_mutex_destroy(&consumed_lock);
	pthread_mutex_destroy(&producer_id_lock);
	pthread_mutex_destroy(&consumer_id_lock);
	pthread_mutex_destroy(&elapsed_lock);
	pthread_mutex_destroy(&output_lock);

	fileout.close();
}

void* producer(void* threadID) {
  //TODO: producer thread, see instruction for implementation
	pthread_mutex_lock(&producer_id_lock);
	int thread_id = ++producer_id;
	pthread_mutex_unlock(&producer_id_lock);

	while(true) {
		pthread_mutex_lock(&produced_lock);
		if (produced == num_items) {
			pthread_mutex_unlock(&produced_lock);
			return NULL;
		}
		int item = ++produced;
		pthread_mutex_unlock(&produced_lock);

		buffer.append(item);

		pthread_mutex_lock(&output_lock);
		pthread_mutex_lock(&produced_lock);
		fileout << "Producer #" << thread_id << ", time = " << chrono::duration_cast<chrono::seconds>(elapsed).count() << "s, producing data item #" << produced << " with value " << item << endl;
		pthread_mutex_unlock(&produced_lock);
		pthread_mutex_unlock(&output_lock);

		pthread_mutex_lock(&elapsed_lock);
		usleep(p_timestep.count() * 1000);
		elapsed += p_timestep;
		pthread_mutex_unlock(&elapsed_lock);
	}
}

void* consumer(void* threadID) {
  //TODO: consumer thread, see instruction for implementation
	pthread_mutex_lock(&consumer_id_lock);
	int thread_id = ++consumer_id;
	pthread_mutex_unlock(&consumer_id_lock);

	while (true) {
		pthread_mutex_lock(&consumed_lock);
		if (consumed == num_items) {
			pthread_mutex_unlock(&consumed_lock);
			return NULL;
		}
		++consumed; 
		pthread_mutex_unlock(&consumed_lock);

		int item = buffer.remove();

		pthread_mutex_lock(&output_lock);
		fileout << "Consumer #" << thread_id << ", time = " << chrono::duration_cast<chrono::seconds>(elapsed).count() << "s, consuming data item with value " << item << endl;
		pthread_mutex_unlock(&output_lock);

		pthread_mutex_lock(&elapsed_lock);
		usleep(c_timestep.count() * 1000);
		elapsed += c_timestep;
		pthread_mutex_unlock(&elapsed_lock);
	}
}