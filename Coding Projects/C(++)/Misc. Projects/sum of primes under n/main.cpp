#include <iostream>
#include "InfInt.h"
using namespace std;

InfInt primeSum(int x) {
    InfInt n = 0;
    vector<int> a;
    for (int i = 0; i < x; i += 1) {
        a.push_back(i);
    }
    for (int i = 2; i*i < a.size(); i += 1) {
        for (int j = i*i; j < a.size(); j += 1) {
            if (a[j] % i == 0) {
                a[j] = 0;
            }
        }
    }
    a.erase(remove(a.begin(), a.end(), 0), a.end());
    a.erase(a.begin());
    for (int i = 0; i < a.size(); i += 1) {
        cout << a[i] << endl;
        n += a[i];
    }
    return n;
}

int main() {
    cout << primeSum(2000000) << endl;
}