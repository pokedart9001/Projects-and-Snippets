#include "challenge.h"

//main function
int main()
{
	int n_threads = 100;
	struct args args = {50, 100};

	init(1);
	reset_accs();

	pthread_t tid[n_threads];

	for (int i = 0; i < n_threads; i++)
		pthread_create(&tid[i], NULL, count_up, &args);
	for (int i = 0; i < n_threads; i++)
		pthread_join(tid[i], NULL);

	set_val(get_acc_up());

	for (int i = 0; i < n_threads; i++)
		pthread_create(&tid[i], NULL, count_down, &args);
	for (int i = 0; i < n_threads; i++)
		pthread_join(tid[i], NULL);
	
	printf("acc_up: %i\n", get_acc_up());
	printf("acc_down: %i\n", get_acc_down());
	printf("val: %i\n", get_val());

	clean();
}