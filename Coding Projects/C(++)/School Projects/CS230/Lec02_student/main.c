#include "challenge.h"

// main function for non-test code
// This exists solely for the benefit of the students to test their own code
int main()
{
  int arr1[] = {1, 2, 3, 4, 5};
  scale(5, arr1, 2);
  printf("%i %i %i %i %i\n", arr1[0], arr1[1], arr1[2], arr1[3], arr1[4]);

  int arr2[][3] = {
  	{1, 2, 3},
  	{4, 5, 6},
  	{7, 8, 9},
  	{10, 11, 12}
  };
  int flat[12];
  flatten(4, arr2, flat);
  for (int i = 0; i < 12; i++)
  	printf("%i ", flat[i]);
  printf("\n");
}
