#include "lisp.h"
#include <iostream>

using namespace std;

int main(int argc, char** argv) {
    cout << "first: " << Eval<DIV, Int<4>, Int<3>>::value << endl;
//    cout << "first: " << Eval<PLUS, Int<1>, Int<2>>::value << endl;
    cout << "second: " << Eval<LPAR, PLUS, LPAR, MUL, Int<1>, Int<2>, RPAR, Int<3>, RPAR>::value << endl;
    cout << "third: " << Eval<COND, Int<1>, Int<1>, Int<0>>::value << endl;
    cout << "fourth: " << Eval<COND, Int<0>, Int<1>, Int<5>>::value << endl;
    cout << "fifth: " << Eval<COND, EQ, Int<5>, PLUS, Int<2>, Int<2>, Int<2>, PLUS, Int<3>, Int<0>>::value << endl;

}
