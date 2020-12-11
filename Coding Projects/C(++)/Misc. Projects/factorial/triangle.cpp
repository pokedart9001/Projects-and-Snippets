#include <stdio.h>
#include <iostream>

int triangle(int n) {
    int x;
    if (n == 0) {
        return 0;
    } else if (n < 0) {
        x = n+1;
    } else {
        x = n-1;
    }
    return n+triangle(x);
}