#include "NoLockStack.h"

// Global variable so it can be used in all threads
NoLockStack my_stack;

int main();
void* producer(void* param);
void* consumer(void* param);