#include <stdio.h>
#include <iostream>

int factorial(int n) {
    int x = n-1;
    if (n == 0) {
        return 1;
    } else {
        return n*factorial(x);
    }
}