#include <iostream>
#include "InfInt.h"
using namespace std;

InfInt power(InfInt n, InfInt p) {
    if (p == 0) {
        return 1;
    } else if (p == 1) {
        return n;
    } else {
        return n*power(n, p-1);
    }
}

InfInt powerSum(InfInt x) {
    if (x < 1) {
        return 0;
    } else {
        return power(x, x) + powerSum(x-1);
    }
}

int main() {
    cout << powerSum(1000) << endl;
}
