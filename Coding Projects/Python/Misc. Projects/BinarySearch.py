def search(arr, val):
	bot = 0
	top = len(arr)
	while top - 1 != bot:
		mid = (top + bot) // 2
		if val in arr[bot:mid]:
			top = mid
		elif val in arr[mid:top]:
			bot = mid
		else:
			return -1
	return bot

arr = [14, 5, 8, 23, 67]
print(search(arr, 6))