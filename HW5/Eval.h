

#ifndef HW5_EVAL_H
#define HW5_EVAL_H
#include <iostream>
#include <typeinfo>
#include "lisp.h"


template<typename... TT>
struct Tuple {int size = sizeof...(TT);};

template <typename ... T> struct Eval {
    static constexpr int first=0;
    static constexpr int sec=0;
    static constexpr int value = 0;
    static constexpr int drop_amount = 0;
};

template <bool g, typename T, typename E>
struct IF {
    static constexpr int value = Eval<T>::value;
};
template <typename T, typename E>
struct IF<false, T, E> {
    static constexpr int value = Eval<E>::value;
};

template <int N, typename T>
struct Drop{
    constexpr static int first = 0;
};

template <int N, typename T, typename ... TT>
struct Drop<N, Tuple<T, TT...>>{
    constexpr static int first = Drop<N-1, Tuple<TT...>>::first;
};

template <typename T, typename ... TT>
struct Drop<0, Tuple<T, TT...>>{
    constexpr static int first = Eval<T>::value;
};

template <typename, typename>
struct SameType;
template <typename T, typename U>
struct SameType {
    static const bool result = false;
};
template <typename T>
struct SameType<T,T> {
    static const bool result = true;
};

template <class T>
struct Eval<T> {
    static constexpr int first=T::value;
    static constexpr int sec=0;
    static constexpr int value = first;
    static constexpr int drop_amount = 1;
};

template<int N>
struct Factorial {
    constexpr static int value = N*Factorial<N-1>::value;
};
template <>
struct Factorial<0> {
    constexpr static int value = 1;
};


template<typename T, typename... TT>
struct TypeList {
    typedef T head;
    typedef TypeList<TT...> tail;
};

template<typename... TT>
struct TypeList<TypeList<TT...>> {
    typedef TypeList<TT...> tail;
};

template<int N, typename T, typename... TT>
struct SubList{
    typedef TypeList<T, typename SubList<N-1, TT...>::sublist::head> sublist;
};

template<typename T, typename... TT>
struct SubList<1, T, TT...>{
    typedef TypeList<T> sublist;
};


template <class OP, class T, class U>
struct Eval<OP, T, U> {
    static constexpr int first = Drop<Eval<OP>::drop_amount,Tuple<OP, T, U>>::first;
    static constexpr int sec = Drop<Eval<T>::drop_amount, Tuple<T, U>>::first;
    static constexpr int value = IF<SameType<OP,PLUS>::result,Int<first + sec>,
                                 IF<SameType<OP,MINUS>::result,Int<first - sec>,
                                 IF<SameType<OP,MUL>::result,Int<first * sec>,
                                 IF<SameType<OP,DIV>::result,Int<first / sec>,
                                 IF<SameType<OP,EQ>::result,Int<IF<first == sec, Int<1>, Int<0>>::value>,Int<0>>>>>>::value;
    static constexpr int drop_amount = Eval<OP>::drop_amount + Eval<T>::drop_amount + Eval<U>::drop_amount;
};


template <class ONEARG, class T>
struct Eval<ONEARG, T> {
    static constexpr int first = Drop<Eval<ONEARG>::drop_amount,Tuple<ONEARG, T>>::first;
    static constexpr int sec = 0;
    static constexpr int value = IF<SameType<ONEARG,NOT>::result,Int<IF<first == 0, Int<1>, Int<0>>::value>,
                                 IF<SameType<ONEARG, FACT>::result,Int<Factorial<first>::value>,Int<0>>>::value;
    static constexpr int drop_amount = Eval<ONEARG>::drop_amount + Eval<T>::drop_amount;
};

template <class COND, class T, class U, class W>
struct Eval<COND, T, U, W> {
    static constexpr int cond = Drop<Eval<COND>::drop_amount,Tuple<COND, T, U, W>>::first;
    static constexpr int first = Drop<Eval<T>::drop_amount,Tuple<T, U, W>>::first;
    static constexpr int sec = Drop<Eval<U>::drop_amount,Tuple<U, W>>::first;;
    static constexpr int value = IF<cond != 0, Int<first>, Int<sec>>::value;
    static constexpr int drop_amount = Eval<COND>::drop_amount + Eval<T>::drop_amount + Eval<U>::drop_amount + Eval<W>::drop_amount;
};

template <class TOKEN, int N, typename T, typename ...TT>
struct Parse{

};

template <class TOKEN, typename T, typename ...TT>
struct Parse<TOKEN, 0, T, TT...> {

};


/*template <int N, typename TT>
struct GetFirst{};

template <int N, typename TT, typename ...TT>
struct GetFirst<Tuple<TT, TT...>{
    typedef GetFirst<N-1, Tuple<TT...>>::head;
};

template <int N, typename TT, typename ...TT>
struct GetFirst<0, Tuple<TT, TT...>>{
    typedef TT head;
};*/



/*
template <typename TT, typename ... TT>
struct Eval<TT, TT...> {
    static constexpr  int value = Eval <TT...>::value;
    static constexpr int drop_amount;
};*/
#endif //HW5_EVAL_H
