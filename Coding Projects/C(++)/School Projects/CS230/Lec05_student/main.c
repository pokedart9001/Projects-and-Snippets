#include "challenge.h"

// main function for non-test code
// This exists solely for the benefit of the students to test their own code
int main()
{
	struct Cred *head = cred("Noah", "p@s5w0rd", NULL);
	printf("[%s, %s]\n", head->username, head->password);
}