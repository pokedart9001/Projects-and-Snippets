#include "main.h"

int main() {
    pthread_t pids[100];
    pthread_t cids[100];
    for(int i = 0; i < 100; i++){
        pthread_create(&pids[i], NULL, producer, NULL);
    }

    for(int i = 0; i < 100; i++){
        pthread_create(&cids[i],NULL, consumer, NULL);
    }

    for(int i = 0; i < 100; i++){
        pthread_join(pids[i], NULL);
    }

    for(int i = 0; i < 100; i++){
        pthread_join(cids[i], NULL);
    }
}

void* producer(void* param){
    my_stack.push(rand());
}

void* consumer(void* param){
    printf("%d\n", my_stack.pop()); // We use printf because it's thread-safe, iostreams can get interrupted
}