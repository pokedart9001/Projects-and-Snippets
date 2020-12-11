def bubblesort(arr):
	unsorted = True;
	while unsorted:
		unsorted = False
		for i in range(len(arr) - 1):
			if arr[i] > arr[i+1]:
				swap(arr, i, i+1)
				unsorted = True
	return arr

def swap(arr, index1, index2):
	temp = arr[index1]
	arr[index1] = arr[index2]
	arr[index2] = temp

print(bubblesort([9, 8, 7, 6, 5, -4, 3, -2, 0, 2]))