#include "challenge.h"

// main function for non-test code
// This exists solely for the benefit of the students to test their own code
int main()
{
	struct Time t = elapsed_time(create_time(2, 27, 22), create_time(5, 27, 10));
	printf("%02i:%02i:%02i\n", t.hour, t.min, t.sec);
	return 0;
}