#include "SafeStack.h"

SafeStack::SafeStack(){
    //TODO
    numbers = new stack<int>();
}
SafeStack::~SafeStack(){
    //TODO
    pthread_mutex_destroy(&stack_lock);
    pthread_cond_destroy(&not_empty);
    delete numbers;
}
void SafeStack::push(int data){
    //TODO
    pthread_mutex_lock(&stack_lock);
    numbers->push(data);
    pthread_cond_signal(&not_empty);
    pthread_mutex_unlock(&stack_lock);
}
int SafeStack::pop(){
    int temp;
    // Lock
    pthread_mutex_lock(&stack_lock);
    // Wait for stuff to be in stack
    while(numbers->size() == 0){
        pthread_cond_wait(&not_empty, &stack_lock);
    }
    // Get top
    temp = numbers->top();
    // Get rid of top
    numbers->pop();
    // Unlock (because we already have top)
    pthread_mutex_unlock(&stack_lock);
    // Return
    return temp;
}
int SafeStack::size(){
    //TODO
    return numbers->size();
}