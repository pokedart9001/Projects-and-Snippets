#include <iostream>
#include "main.h"
using namespace std;

int main() {
    int n;
    cout << "Enter a number: ";
    cin >> n;
    while(!cin) {
        cout << "NaN" << endl;
        cin.clear();
        cin.ignore(256,'\n');
        cout << "Enter a number: ";
        cin >> n;
    }
    if (n < 0) {
        cout << "Factorial not available (" << n << " is not positive)" << endl;
    } else {
        cout << "The factorial of this number is: " << factorial(n) << endl;
    }
    cout << "The triangle of this number is: " << triangle(n) << endl;
    return 0;
}