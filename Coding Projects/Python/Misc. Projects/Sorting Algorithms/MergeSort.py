def mergesort(arr):
	if len(arr) < 2:
		return arr
	part1 = arr[0:len(arr)//2]
	part2 = arr[len(arr)//2:]
	return merge(mergesort(part1), mergesort(part2))

def merge(arr1, arr2):
	if len(arr1) == 0 or len(arr2) == 0:
		return arr1 + arr2
	elif arr1[0] < arr2[0]:
		return arr1[0:1] + merge(arr1[1:], arr2)
	else:
		return arr2[0:1] + merge(arr1, arr2[1:])

print(mergesort([9, 8, 7, 6, 5, -4, 3, -2, 0, 2]))