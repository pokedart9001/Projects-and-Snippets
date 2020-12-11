def insertionsort(arr):
	for i in range(1, len(arr)):
		x = arr[i]
		index = i - 1
		while index >= 0 and arr[index] > x:
			arr[index+1] = arr[index]
			index -= 1
		arr[index+1] = x
	return arr

print(insertionsort([9, 8, 7, 6, 5, -4, 3, -2, 0, 2]))