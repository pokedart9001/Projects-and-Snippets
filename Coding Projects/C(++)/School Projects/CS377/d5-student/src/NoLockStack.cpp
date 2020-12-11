#include "NoLockStack.h"

NoLockStack::NoLockStack(){
	//TODO
	head = NULL;
	count = 0;
}

NoLockStack::~NoLockStack() {
	// Iterate through all elements and free them.
	while(head != NULL) {
		Node* temp = head;
		head = (*head).next; // std::atomic doesn't like -> operators
		delete temp;
	}
}

/** Creates a new node from the value passed in and makes that new node the head
 *  of the stack. Also incrememnts count.
 * 
 *  @param data The int to be stored in the stack.
 */
void NoLockStack::push(int data) {
	//TODO
	Node* n = new Node(data);
	n->next = head;
	while (!head.compare_exchange_weak(n->next, n)) {
		n->next = head;
	}
	count++;
}

/** Gets the head node, remove it, and return it's value. Also decrements count.
 * 
 *  @param t An int passed by reference, such that you can set it equal to the value 
 *           removed and it will update the variable passed in when calling.
 *  @returns true if there was an item to pop and it popped, false otherwise.
 */
bool NoLockStack::pop(int& t) {
	//TODO
	while (head) {
		Node* temp = head;
		while (!head.compare_exchange_weak(temp, (*head).next)) {
			temp = head;
		}
		t = temp->data;
		delete temp;
		count--;
		return true;
	}
	return false;
}

/** Gets the number of elements in the stack.
 * 
 *  @returns An int representing the number of elements in the stack.
 */
int NoLockStack::size() {
	//TODO
	return count;
}