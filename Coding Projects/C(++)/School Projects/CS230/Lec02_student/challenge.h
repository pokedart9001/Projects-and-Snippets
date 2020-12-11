#include <stdio.h>

#ifndef CH_HEAD
#define CH_HEAD

// goal: scales (the first N elements of) an array
// param N: number of elements to scale
// param arr: the array to scale
// param scale: the value to scale the array by
void scale(int N, int arr[], int scale);

// To Students: You'll often be told to comment code, let me tell you about the two kinds of comments
//   Comments that explain "what":
//     What code is doing
//     This is often what we look for, but in the real world, this is the less important variety
//   Comments that explain "why":
//     Why was something done a certain way
//     This is to explain design choices and certain eccentricities so future developers understand 
//      why something was done in a particular way.
//   Good rant on ATP episode 326 at time 1:00:50, maybe 1:03:00 (https://atp.fm/episodes/326)
//   Below is an example of "why"
//

// goal: flattens (the first N elements of) an array
// param N: number of elements to scale
// param arr: the array to flatten
// param flat: the array to store the flattened version of arr in
// assumptions:
//   arr has dimensions N by 3, (arr[N][3])
//   flat has length 3*N
void flatten(int N, int arr[][3], int flat[]);

#endif
