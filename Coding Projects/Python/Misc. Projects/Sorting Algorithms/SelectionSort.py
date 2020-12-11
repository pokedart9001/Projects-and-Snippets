def selectionsort(arr):
	for i in range(len(arr)):
		swap(arr, i, minIndex(arr, i))
	return arr

def swap(arr, index1, index2):
	temp = arr[index1]
	arr[index1] = arr[index2]
	arr[index2] = temp

def minIndex(arr, start):
	return arr.index(min(arr[start:]))

print(selectionsort([9, 8, 7, 6, 5, -4, 3, -2, 0, 2]))