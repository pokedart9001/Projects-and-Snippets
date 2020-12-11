def pan(n):
	string = str(n)
	result = True
	for i in range(1, 10):
		if len(string) < 18 or not(str(i) in string[:9] and str(i) in string[-9:]):
			result = False
	return result

count = 2

a = 1
b = 1

while True:
	a += b
	count += 1
	if pan(a):
		break
	b += a
	count += 1
	if pan(b):
		break

print count