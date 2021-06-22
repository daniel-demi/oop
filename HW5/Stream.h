
#ifndef HW5_STREAM_H
#define HW5_STREAM_H

#include <functional>
#include <map>
template<class T>
class Stream {
    Stream() = delete;

    explicit Stream(std::function<T*()>);

public:

    std::function<T *()> function;

    template<template<typename> class TContainer>
    static Stream<T> of(TContainer<T *>);

    template<class S>
    static Stream<T> of(std::map<S,T>);

    virtual Stream<T> filter(std::function<bool(const T *)>);

    template<class R>
    Stream<R> map(std::function<R *(const T *)>);

    Stream<T> distinct(std::function<bool(const T *, const T *)>);

    Stream<T> distinct();

    template<template<typename> class TContainer>
    TContainer<T *> collect(TContainer<T*>);

    void forEach(std::function<void(T*)>);

    T* reduce(T*, std::function<T*(const T*, const T*)>);

    T* min();

    T* max();

    int count();

    bool allMatch(bool(const T*));

    bool anyMatch(bool(const T*));

    T* firstFirst(bool(const T*));
};

template<class T>
class StreamFilter : protected Stream<T> {
    Stream<T> filter(std::function<bool(const T*)>);
};




#endif //HW5_STREAM_H
