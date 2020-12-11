def aSum(n, a1, aN):
	return int((n/2.0)*(a1 + aN))

def solveN(a1, aN, Sn):
	return int((Sn*2.0)/(a1 + aN))

def solveD(n, a1, aN):
	return int((aN - a1)/(n - 1.0))

def solveA1(n, aN, Sn):
	return int((Sn/(n/2.0)) - aN)

def firstThree(a1, d):
	return [a1, a1 + d, a1 + 2*d]

aN = 5.8
Sn = 45
n = 15
a1 = solveA1(n, aN, Sn)
d = solveD(n, a1, aN)
print firstThree(a1, d)