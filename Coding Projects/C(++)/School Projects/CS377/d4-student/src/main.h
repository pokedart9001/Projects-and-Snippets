#include "SafeStack.h"

// Global variable so it can be used in all threads
SafeStack my_stack;

int main();
void* producer(void* param);
void* consumer(void* param);