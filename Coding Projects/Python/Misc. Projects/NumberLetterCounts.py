ones = ('', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine')
teens = ('ten', 'eleven', 'twelve', 'thirteen', 'fourteen', 'fifteen', 'sixteen', 'seventeen', 'eighteen', 'nineteen')
tens = ('', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety')


def wordNum(n):
	num = str(n)
	result = ''
	if len(num) == 1:
		result += ones[n]
	elif len(num) == 2:
		if int(num[0]) == 1:
			result += teens[int(num[1])]
		else:
			result += tens[int(num[0])] + ones[int(num[1])]
	elif len(num) == 3:
		result += ones[int(num[0])]
		result += 'hundred' if int(num[1:]) == 0 else 'hundredand' + wordNum(int(num[1:]))
	else:
		result += ones[int(num[0])] + 'thousand'
	return result

count = 0
for i in range(1000):
	count += len(wordNum(i + 1))

print count