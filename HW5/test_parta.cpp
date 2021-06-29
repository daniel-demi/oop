#include <iostream>
#include "lisp.h"

using namespace std;

bool passed_all = true;
unsigned checks_done = 0;

void printBool(bool b){
    cout << (b ? "TRUE" : "FALSE");
}

void check(int x, int y){
    cout << (x == y ? "PASS" : "FAIL") << ",    check number: " << checks_done << endl;
    cout << "       result: " << x << ", expected: " << y << endl;
    passed_all = passed_all&&(x == y);

    ++checks_done;
}

int main(){
    //check number 0:
    check(Eval<COND, Int<1>, Int<2>, Int<3>>::value, 2);
    check(Eval<COND, Int<0>, Int<2>, Int<3>>::value, 3);
    check(Eval<COND, EQ, Int<39>, Int<17>, PLUS, Int<10>, Int<30>, MINUS, Int<9>, Int<3>>::value, 6);
    //check number 3:
    check(Eval<Int<24>>::value, 24);
    check(Eval<COND, Int<1>, Int<24>, PLUS, Int<1>, Int<3>>::value, 24);
    check(Eval<COND, Int<1>, COND, Int<1>, Int<24>, Int<2>, PLUS, Int<1>, Int<3>>::value, 24);
    //check number 6:
    check(Eval<COND, Int<1>, COND, Int<1>, FACT, Int<4>, Int<2>, PLUS, Int<1>, Int<3>>::value, 24);
    check(Eval<COND, Int<1>, COND, Int<1>, LPAR, LPAR, FACT, Int<4>, RPAR, RPAR, Int<2>, PLUS, Int<1>, Int<3>>::value, 24);
    // (cond 1 (cond 1 ((fact 4)) (2)) (+ 1 3))
    check(Eval<COND, COND, Int<0>, Int<9>, LPAR, Int<1>, RPAR, COND, Int<1>, LPAR, LPAR, FACT, Int<4>, RPAR, RPAR, LPAR, Int<2>, RPAR, PLUS, Int<1>, Int<3>>::value, 24);    
    //check number 9:
    check(Eval<COND, COND, Int<0>, Int<9>, LPAR, Int<1>, RPAR, Int<2>, Int<3>>::value, 2);
    check(Eval<COND, COND, Int<0>, Int<9>, Int<1>, Int<2>, Int<3>>::value, 2);
    check(Eval<
        PLUS, 
            LPAR, COND,
                COND,
                    Int<0>,
                    Int<9>,
                    LPAR, Int<1>, RPAR,
                COND,
                    Int<1>,
                    LPAR, LPAR, FACT, Int<4>, RPAR, RPAR,
                    LPAR, Int<2>, RPAR,
                PLUS, Int<1>, Int<3>,
            RPAR,
            Int<1>
        >::value, 25);    
    //check number 12:
    check(Eval<FACT, LPAR, MINUS, LPAR, Int<5>, RPAR, LPAR, Int<2>, RPAR, RPAR>::value, 6);
    check(Eval<EQ, PLUS, Int<88>, Int<12>, Int<100>>::value, 1);
    check(Eval<LPAR, EQ, PLUS, LPAR, Int<88>, RPAR, Int<12>, MUL, Int<10>, Int<10>, RPAR>::value, 1);
    //check number 15:
    check(Eval<PLUS, NOT, LPAR, EQ, PLUS, LPAR, Int<88>, RPAR, Int<12>, MUL, Int<10>, Int<10>, RPAR, Int<99>>::value, 99);
    check(Eval<DIV, Int<20>, Int<10>>::value, 2);
    check(Eval<MUL, EQ, NOT, FACT, Int<4>, Int<0>, Int<25>>::value, 25);
    //check number 18:
    check(Eval<NOT, NOT, NOT, NOT, NOT, Int<0>>::value, 1);
    check(Eval<LPAR, NOT, NOT, LPAR, NOT, NOT, LPAR, LPAR, NOT, Int<0>, RPAR, RPAR, RPAR, RPAR>::value, 1);
    check(Eval<COND, EQ, COND, Int<1>, Int<52>, Int<39>, Int<52>, Int<13>, Int<99>>::value, 13);
    //check number 21:
    check(Eval<DIV, MUL, Int<9>, DIV, Int<19>, Int<2>, Int<4>>::value, 20);
    check(Eval<EQ, DIV, MUL, Int<9>, DIV, Int<19>, Int<2>, Int<4>, Int<19>>::value, 0);
    check(Eval<LPAR, EQ, LPAR, LPAR, Int<18>, RPAR, Int<18>, RPAR, RPAR, RPAR>::value, 1);

    cout << "passed all = ";
    printBool(passed_all);
    cout << endl;
}
