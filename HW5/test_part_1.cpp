#include "lisp.h"
#include <iostream>
using namespace std;

static int check = 0;
void assertBasicWithPar(int num1, int num2)
{
    if(num1 != num2)
    {
        cerr<<"assertion failed at basic test with parenthesis!"<<endl;
        check++;
    }
}

void assertBasicNoPar(int num1, int num2)
{
    if(num1 != num2)
    {
        cerr<<"assertion failed at basic test without parenthesis!"<<endl;
        check++;
    }
}

void assertAdvancedMathWithPar(int num1, int num2)
{
    if(num1 != num2)
    {
        cerr<<"assertion failed at advanced math test with parenthesis!"<<endl;
        check++;
    }
}

void assertAdvancedMathNoPar(int num1, int num2)
{
    if(num1 != num2)
    {
        cerr<<"assertion failed at advanced math test without parenthesis!"<<endl;
        check++;
    }
}

void assertAdvancedCondWithPar(int num1, int num2)
{
    if(num1 != num2)
    {
        cerr<<"assertion failed at advanced condition test with parenthesis!"<<endl;
        check++;
    }
}

void assertAdvancedCondNoPar(int num1, int num2)
{
    if(num1 != num2)
    {
        cerr<<"assertion failed at advanced condition test without parenthesis!"<<endl;
        check++;
    }
}


void assertSuccess()
{
    if (check == 0)
        cout<<"you have successfully passed the tests!"<<endl;
    else
    {
        cout<<check<<" tests failed out of 6"<<endl;
        check = 0;
    }
}

int main() {
    /*
     * TESTS LIST:
     *
     * 1. BASIC OPERATIONS:
     *    1.1 with parenthesis
     *    1.2 without parenthesis
     * 2. ADVANCED MATH OPERATIONS:
     *    2.1 with parenthesis
     *    2.2 without parenthesis
     * 3. ADVANCED CONDITION OPERATIONS:
     *    3.1 with parenthesis
     *    3.2 without parenthesis
     */

    /*
     * 1. BASIC OPERATIONS:
     *    1.1 with parenthesis
     */
    assertBasicWithPar(Eval<LPAR, FACT, Int<5>, RPAR>::value, 120);
    assertBasicWithPar(Eval<LPAR, EQ, Int<1>, Int<2>, RPAR>::value, 0);
    assertBasicWithPar(Eval<LPAR, PLUS, Int<1>, Int<2>, RPAR>::value, 3);
    assertBasicWithPar(Eval<LPAR, MINUS, Int<1>, Int<2>, RPAR>::value, -1);
    assertBasicWithPar(Eval<LPAR, MUL, Int<10>, Int<2>, RPAR>::value, 20);
    assertBasicWithPar(Eval<LPAR, DIV, Int<64>, Int<8>, RPAR>::value, 8);
    assertBasicWithPar(Eval<LPAR, COND, Int<1>, Int<2>, Int<3>, RPAR>::value, 2);
    assertBasicWithPar(Eval<LPAR, NOT, Int<1>, RPAR>::value, 0);
    /*
     *    1.2 without parenthesis
     */
    assertBasicNoPar(Eval<FACT, Int<5>>::value, 120);
    assertBasicNoPar(Eval<EQ, Int<1>, Int<2>>::value, 0);
    assertBasicNoPar(Eval<PLUS, Int<1>, Int<2>>::value, 3);
    assertBasicNoPar(Eval<MINUS, Int<1>, Int<2>>::value, -1);
    assertBasicNoPar(Eval<MUL, Int<10>, Int<2>>::value, 20);
    assertBasicNoPar(Eval<DIV, Int<64>, Int<8>>::value, 8);
    assertBasicNoPar(Eval<COND, Int<1>, Int<2>, Int<3>>::value, 2);
    assertBasicNoPar(Eval<NOT, Int<1>>::value, 0);
    /*
     * 2. ADVANCED MATH OPERATIONS:
     *    2.1 with parenthesis
     */
    assertAdvancedMathWithPar(Eval<LPAR, PLUS, Int<5>, LPAR, PLUS, Int<3>, Int<2>, RPAR, RPAR>::value, 10);
    assertAdvancedMathWithPar(Eval<LPAR, PLUS, PLUS, Int<3>, Int<2>, Int<1>, RPAR>::value, 6);
    assertAdvancedMathWithPar(Eval<LPAR, PLUS, Int<5>, LPAR, PLUS, Int<3>, LPAR, PLUS, Int<3>, Int<2>, RPAR, RPAR, RPAR>::value, 13);
    assertAdvancedMathWithPar(Eval<LPAR, PLUS, MINUS, Int<3>, Int<2>, Int<1>, RPAR>::value, 2);
    assertAdvancedMathWithPar(Eval<LPAR, MUL, MUL, Int<3>, Int<2>, MINUS, FACT, Int<4>, Int<5>, RPAR>::value, 114);
    assertAdvancedMathWithPar(Eval<LPAR, MUL, LPAR, PLUS, Int<1>, Int<2>, RPAR, Int<3>, RPAR>::value, 9);
    assertAdvancedMathWithPar(Eval<LPAR, MUL, LPAR, FACT, Int<4>, RPAR, PLUS, PLUS, Int<2>, Int<3>, Int<4>, RPAR>::value , 216);
    /*
     *    2.2 without parenthesis
     */
    assertAdvancedMathNoPar(Eval<PLUS, Int<5>, LPAR, PLUS, Int<3>, Int<2>, RPAR>::value, 10);
    assertAdvancedMathNoPar(Eval<PLUS, PLUS, Int<3>, Int<2>, Int<1>>::value, 6);
    assertAdvancedMathNoPar(Eval<PLUS, Int<5>, LPAR, PLUS, Int<3>, LPAR, PLUS, Int<3>, Int<2>, RPAR, RPAR>::value, 13);
    assertAdvancedMathNoPar(Eval<PLUS, MINUS, Int<3>, Int<2>, Int<1>>::value, 2);
    assertAdvancedMathNoPar(Eval<MUL, MUL, Int<3>, Int<2>, MINUS, FACT, Int<4>, Int<5>>::value, 114);
    assertAdvancedMathNoPar(Eval<MUL, PLUS, Int<1>, Int<2>, Int<3>>::value, 9);
    assertAdvancedMathNoPar(Eval<MUL, FACT, Int<4>, PLUS, PLUS, Int<2>, Int<3>, Int<4>>::value , 216);
    /*
     * 3. ADVANCED CONDITION OPERATIONS:
     *    3.1 with parenthesis
     */
    assertAdvancedCondWithPar(Eval<LPAR, EQ, Int<6>, LPAR, PLUS, Int<4>, Int<2>, RPAR, RPAR>::value, 1);
    assertAdvancedCondWithPar(Eval<LPAR, COND, EQ, LPAR, PLUS, Int<3>, Int<2>, RPAR, LPAR, DIV, Int<10>, Int<2>, RPAR, Int<1>, Int<0>, RPAR>::value, 1);
    assertAdvancedCondWithPar(Eval<LPAR, COND, EQ, Int<5>, LPAR, PLUS, Int<5>, Int<2>, RPAR, Int<2>, LPAR, PLUS, Int<2>, Int<7>, RPAR, RPAR>::value, 9);
    assertAdvancedCondWithPar(Eval<LPAR, EQ, LPAR, PLUS, Int<8>, Int<7>, RPAR, LPAR, PLUS, Int<3>, LPAR, PLUS, Int<3>, Int<2>, RPAR, RPAR, RPAR>::value, 0);
    assertAdvancedCondWithPar(Eval<LPAR, COND, LPAR, Int<1>, RPAR, LPAR, Int<3>, RPAR, LPAR, Int<4>, RPAR, RPAR>::value, 3);
    assertAdvancedCondWithPar(Eval<LPAR, COND, LPAR, EQ, Int<1>, Int<0>, RPAR, Int<3>, Int<5>, RPAR>:: value, 5);
    assertAdvancedCondWithPar(Eval<LPAR, COND, LPAR, EQ, Int<5>, LPAR, PLUS, Int<3>, Int<2>, RPAR, RPAR, Int<2>, LPAR, PLUS, Int<2>, Int<0>, RPAR, RPAR>::value, 2);
    /*
     *    3.2 without parenthesis
     */
    assertAdvancedCondNoPar(Eval<EQ, Int<6>, PLUS, Int<4>, Int<2>>::value, 1);
    assertAdvancedCondNoPar(Eval<COND, EQ, PLUS, Int<3>, Int<2>, DIV, Int<10>, Int<2>, Int<1>, Int<0>>::value, 1);
    assertAdvancedCondNoPar(Eval<COND, EQ, Int<5>, PLUS, Int<5>, Int<2>, Int<2>, PLUS, Int<2>, Int<7>>::value, 9);
    assertAdvancedCondNoPar(Eval<EQ, PLUS, Int<8>, Int<7>, PLUS, Int<3>, PLUS, Int<3>, Int<2>>::value, 0);
    assertAdvancedCondNoPar(Eval<COND, LPAR, Int<1>, RPAR, LPAR, Int<3>, RPAR, LPAR, Int<4>, RPAR>::value, 3);
    assertAdvancedCondNoPar(Eval<COND, EQ, Int<1>, Int<0>, Int<3>, Int<5>>:: value, 5);
    assertAdvancedCondNoPar(Eval<COND, EQ, Int<5>, PLUS, Int<3>, Int<2>, Int<2>, PLUS, Int<2>, Int<0>>::value, 2);

    assertSuccess();
}




