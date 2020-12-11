from random import shuffle

iters = 0

def bogosort(arr):
	while not sorted(arr):
		global iters
		iters += 1
		shuffle(arr)
	return arr

def sorted(arr):
	if len(arr) < 2:
		return True
	elif arr[0] > arr[1]:
		return False
	else:
		return sorted(arr[1:])

print(bogosort([9, 8, 7, 6, 5, -4, 3, -2, 0, 2]))
print("Iterations: " + str(iters))