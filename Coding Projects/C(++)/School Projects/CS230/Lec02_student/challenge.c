#include "challenge.h"

// goal: scales (the first N elements of) an array
// param N: number of elements to scale
// param arr: the array to scale
// param scale: the value to scale the array by
//
// TODO: Complete the function
void scale(int N, int arr[], int scale)
{
	for (int i = 0; i < N; i++)
		arr[i] *= scale;
}

// See rant in header file

// goal: flattens (the first N rows of) an array
// param N: number of elements to scale
// param arr: the array to flatten
// param flat: the array to store the flattened version of arr in
// assumptions:
//   arr has dimensions N by 3, (arr[N][3])
//   flat has length 3*N
// example:
//   inputs:
//     N = 4
//     arr = [[ 1,  2,  3],
//            [ 4,  5,  6],
//            [ 7,  8,  9],
//            [10, 11, 12]]
//     flat = ___
//   outcomes:
//     flat = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
//
// TODO: Complete the function
void flatten(int N, int arr[][3], int flat[])
{
	for (int r = 0; r < N; r++) {
		for (int c = 0; c < 3; c++) {
			flat[c + r*3] = arr[r][c];
		}
	}
}
