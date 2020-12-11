a = 1
b = 1
count = 2

while len(str(a)) != 1000 and len(str(b)) < 1000:
	a += b
	count += 1
	b += a
	count += 1

print count