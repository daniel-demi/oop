


#ifndef HW5_LISP_H
#define HW5_LISP_H

//#include "Eval.h"
struct TOKEN {};
struct LPAR : public TOKEN {};
struct RPAR : TOKEN {};
struct COND : public TOKEN {};

struct OP : public TOKEN {};
struct PLUS : public OP {};
struct MINUS : public OP {};
struct MUL : public OP {};
struct DIV : public OP {};
struct EQ : public OP {};

struct ONEARG : public TOKEN {};
struct NOT : public ONEARG {};
struct FACT : public ONEARG {};


template <int v>
struct Int {
    static constexpr int value = v;
    static constexpr int drop_amount = 1;
};



#endif //HW5_LISP_H
