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

InfInt powerList(int aMin, int aMax, int bMin, int bMax) {
    vector<InfInt> v;
    for (int a = aMin; a <= aMax; a += 1) {
        for (int b = bMin; b <= bMax; b += 1) {
            v.push_back(power(a, b));
        }
    }
    sort(v.begin(),v.end());
    v.erase(unique(v.begin(), v.end()), v.end());
    return v.size();
}

int main() {
    cout << powerList(2, 100, 2, 100) << endl;
}