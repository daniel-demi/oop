


#ifndef HW5_LISP_H
#define HW5_LISP_H
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
};

template<typename... TT>
struct TypeList {
    typedef TypeList<TT...> list;
};


template<typename T, typename... TT>
struct TypeList<T, TypeList<TT...>> {
    typedef TypeList<T,TT...> list;
};

//#define DEBUG
#ifdef DEBUG

template<class ...TT>
struct StringValue {
    static constexpr char* value = "nan";
};

template<class ...TT>
struct StringValue<TypeList<TT...>> {
    typedef StringValue<TT...> value;
};

template<class ...TT, int N>
struct StringValue<Int<N>,TT...> {
    static constexpr char* value = "Int";
    static constexpr int num = N;
};

template<class ...TT>
struct StringValue<COND,TT...> {
    static constexpr char* value = "COND";
};

template<class ...TT>
struct StringValue<EQ,TT...> {
    static constexpr char* value = "EQ";
};

template<class ...TT>
struct StringValue<PLUS,TT...> {
    static constexpr char* value = "PLUS";
};

template<class ...TT>
struct StringValue<MINUS,TT...> {
    static constexpr char* value = "MINUS";
};

template<class ...TT>
struct StringValue<MUL,TT...> {
    static constexpr char* value = "MUL";
};

template<class ...TT>
struct StringValue<DIV,TT...> {
    static constexpr char* value = "DIV";
};

template<class ...TT>
struct StringValue<FACT,TT...> {
    static constexpr char* value = "FACT";
};

template<class ...TT>
struct StringValue<NOT,TT...> {
    static constexpr char* value = "NOT";
};



#endif



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

template <int N, typename T, typename ... TT>
struct Drop{
    typedef typename Drop<N-1, TT...>::drop drop;
};

template <typename T,typename ... TT>
struct Drop<1, T,TT...>{
    typedef TypeList<TT...> drop;
};

template <typename T, typename ... TT>
struct Drop<0,T, TT...>{
    typedef TypeList<T, TT...> drop;
};

template <bool g, typename T, typename E>
struct IF {
    static constexpr int value = T::value;
};
template <typename T, typename E>
struct IF<false, T, E> {
    static constexpr int value = E::value;
};

template <typename OP, typename ... TT>
struct Eval {
    static constexpr int first = Eval<TT...>::value;
    static constexpr int sec = Eval<typename Drop<Eval<TT...>::drop_amount, TT...>::drop>::value;
    static constexpr int value = IF<SameType<OP,PLUS>::result,Int<first + sec>,
            IF<SameType<OP,MINUS>::result,Int<first - sec>,
            IF<SameType<OP,MUL>::result,Int<first * sec>,
            IF<SameType<OP,EQ>::result,Int<IF<first == sec, Int<1>, Int<0>>::value>,Int<0>>>>>::value;
    static constexpr int drop_amount = 1 + Eval<TT...>::drop_amount + Eval<typename Drop<Eval<TT...>::drop_amount, TT...>::drop>::drop_amount;
};

template <typename ...TT>
struct Eval<DIV, TT...> {
    static constexpr int first = Eval<TT...>::value;
    static constexpr int sec = Eval<typename Drop<Eval<TT...>::drop_amount, TT...>::drop>::value;
    static constexpr int value = Int<first / sec>::value;
    static constexpr int drop_amount = 1 + Eval<TT...>::drop_amount + Eval<typename Drop<Eval<TT...>::drop_amount, TT...>::drop>::drop_amount;
};



template<int N>
struct Factorial {
    constexpr static int value = N*Factorial<N-1>::value;
};
template <>
struct Factorial<0> {
    constexpr static int value = 1;
};


template <int N, class... TT>
struct Eval<Int<N>, TT...> {
    static constexpr int first = N;
    static constexpr int sec = N;
    static constexpr int value = N;
    static constexpr int drop_amount = 1;
};



template <class... TT>
struct Eval<TypeList<TT...>> {
    static constexpr int first = Eval<TT...>::first;
    static constexpr int sec = Eval<TT...>::sec;
    static constexpr int value = Eval<TT...>::value;
    static constexpr int drop_amount = Eval<TT...>::drop_amount;
};

template <class... TT>
struct Eval<LPAR, TT...> {
    static constexpr int first = Eval<TT...>::first;
    static constexpr int sec = Eval<TT...>::sec;
    static constexpr int value = Eval<TT...>::value;
    static constexpr int drop_amount = 2 + Eval<TT...>::drop_amount;
};

template <class ...TT>
struct Eval<FACT, TT...> {
    static constexpr int first = Eval<TT...>::value;
    static constexpr int sec = 0;
    static constexpr int value = Factorial<first>::value;
    static constexpr int drop_amount = 1 + Eval<TT...>::drop_amount;
};

template <class ...TT>
struct Eval<NOT, TT...> {
    static constexpr int first = Eval<TT...>::value;
    static constexpr int sec = 0;
    static constexpr int value = IF<!first,Int<1>,Int<0>>::value;
    static constexpr int drop_amount = 1 + Eval<TT...>::drop_amount;
};

template <class ...TT>
        // first COND cond: Int<1>, COND, Int<1>, LPAR, LPAR, FACT, Int<4>, RPAR, RPAR, Int<2>, PLUS, Int<1>, Int<3> + 1
        // first COND first: COND, Int<1>, LPAR, LPAR, FACT, Int<4>, RPAR, RPAR, Int<2>, PLUS, Int<1>, Int<3> + 1 + 1 + 6 + 1 = + 9
        //  second COND cond: Int<1>, LPAR, LPAR, FACT, Int<4>, RPAR, RPAR, Int<2>, PLUS, Int<1>, Int<3> + 1
        //  second COND first: LPAR, LPAR, FACT, Int<4>, RPAR, RPAR, Int<2>, PLUS, Int<1>, Int<3> + 2 + 2 + 1 + 1 = +6
        // second COND first: LPAR, FACT, Int<4>, RPAR, RPAR, Int<2>, PLUS, Int<1>, Int<3> + 2 + 1 + 1
        // second COND first: FACT, Int<4>, RPAR, RPAR, Int<2>, PLUS, Int<1>, Int<3>  + 1 + 1
        //  second COND first: Int<4>, RPAR, RPAR, Int<2>, PLUS, Int<1>, Int<3> + 1
        // second COND second: Int<2>, PLUS, Int<1>, Int<3> + 1
        // first COND second: PLUS, Int<1>, Int<3> + 1 + 1 + 1= + 3
        // PLUS first:  Int<1>, Int<3> +1
        // PLUS second: Int<3> + 1
struct Eval<COND, TT...> {
    static constexpr int cond = Eval<TT...>::value; // 1
//    static constexpr char* cond_str = StringValue<typename Drop<(Eval<TT...>::drop_amount) + (Eval<typename Drop<Eval<TT...>::drop_amount,TT...>::drop>::value;
    static constexpr int first =Eval<typename Drop<Eval<TT...>::drop_amount,TT...>::drop>::value;
    static constexpr int sec = Eval<typename Drop<((Eval<TT...>::drop_amount) + (Eval<typename Drop<Eval<TT...>::drop_amount,TT...>::drop>::drop_amount)), TT...>::drop>::value;
    static constexpr int value = IF<cond != 0, Int<first>, Int<sec>>::value;
    static constexpr int drop_amount = 1 + Eval<TT...>::drop_amount + Eval<typename Drop<Eval<TT...>::drop_amount,TT...>::drop>::drop_amount +
            Eval<typename Drop<((Eval<TT...>::drop_amount) + (Eval<typename Drop<Eval<TT...>::drop_amount,TT...>::drop>::drop_amount)), TT...>::drop>::drop_amount;
//    static constexpr int drop_amount = 1 + Eval<TT...>::drop_amount + Eval<typename Drop<Eval<TT...>::drop_amount,TT...>::drop>::drop_amount +
//            Eval<typename Drop<Eval<typename Drop<Eval<TT...>::drop_amount,TT...>::drop>::drop_amount, TT...>::drop>::drop_amount;
};


#endif //HW5_LISP_H
