
#ifndef HW5_STREAM_H
#define HW5_STREAM_H

#include <functional>
#include <map>
#include <vector>
#include <utility>
#include <algorithm>
#include <iterator>

template<class T>
class Stream {
//    Stream() = delete;

    explicit Stream(std::function<std::vector<T*>()>);
    std::function<std::vector<T*>()> lambda;
public:



    template<template<typename> class TContainer>
    static Stream<T> of(TContainer<T *>);

    template<class S>
    static Stream<T> of(std::map<S,T *>);

    //copy_if || remove_if
    virtual Stream<T> filter(std::function<bool(const T *)>);

    //transform
    template<class R>
    Stream<R> map(std::function<R *(const T *)>);

    //unique
    Stream<T> distinct(std::function<bool(const T *, const T *)>);

    Stream<T> distinct();

    Stream<T> sorted();

    template<template<typename> class TContainer>
    TContainer<T *> collect();

    void forEach(std::function<void(T*)>);

    T* reduce(T*, std::function<T*(const T*, const T*)>);

    T* min();

    T* max();

    int count();

    bool allMatch(std::function<bool(const T*)>);

    bool anyMatch(std::function<bool(const T*)>);

    T* findFirst(std::function<bool(const T*)>);
};

//template<class T>
//class StreamFilter : public Stream<T> {
//
//
//
//    template<class U>
//    explicit StreamFilter(Stream<U>);
//
//    Stream<T> filter(std::function<bool(const T*)>);
//};
// Example:
// ========
//... (pred) {
//    return Stream([lambda]() -> TContainer {
//        TContainer c = lambda();
//        return std::copy_if(c.begin(), c.end(), pred);
//
//    });
//}

#endif //HW5_STREAM_H
