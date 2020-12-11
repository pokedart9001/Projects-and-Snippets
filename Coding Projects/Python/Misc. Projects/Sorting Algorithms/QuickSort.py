def quicksort(arr):
	if len(arr) < 2:
		return arr
	pivot = median(arr)
	before = filter(lambda x: x < pivot, arr)
	equal = filter(lambda x: x == pivot, arr)
	after = filter(lambda x: x > pivot, arr)
	return quicksort(before) + equal + quicksort(after)

def median(arr):
	dup = arr[:]
	if len(dup) < 3:
		return dup[0]
	else:
		dup.remove(max(dup))
		dup.remove(min(dup))
		return median(dup)

print(quicksort([9, 8, 7, 6, 5, -4, 3, -2, 0, 2]))