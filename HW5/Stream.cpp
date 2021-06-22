//
// Created by ddemikho on 20/06/2021.
//

#include "Stream.h"

template<class T>
Stream<T>::Stream(std::function<T *()> function1) {
    function = function1;
}

template<class T>
template<template<typename> class TContainer>
Stream<T> Stream<T>::of(TContainer<T*> container) {
    return Stream<T>([container]() {return container.begin();});
}

template<class T>
template<class S>
Stream<T> Stream<T>::of(std::map<S, T> map) {
    return Stream<T>([map]() {return map;});
}

template<class T>
Stream<T> Stream<T>::filter(std::function<bool(const T *)> pred) {
    return Stream<T>([pred]() {

    });
}

template<class T>
template<class R>
Stream<R> Stream<T>::map(std::function<R *(const T *)>) {
    return Stream<R>();
}

template<class T>
Stream<T> Stream<T>::distinct(std::function<bool(const T *, const T *)>) {
    return Stream<T>();
}

template<class T>
Stream<T> Stream<T>::distinct() {
    return Stream<T>();
}

template<class T>
template<template<typename> class TContainer>
TContainer<T*> Stream<T>::collect(TContainer<T*>) {
    return nullptr;
}

template<class T>
void Stream<T>::forEach(std::function<void(T *)>) {

}

template<class T>
T *Stream<T>::reduce(T *, std::function<T *(const T *, const T *)>) {
    return nullptr;
}

template<class T>
bool Stream<T>::anyMatch(bool (*)(const T *)) {
    return false;
}

template<class T>
bool Stream<T>::allMatch(bool (*)(const T *)) {
    return false;
}

template<class T>
int Stream<T>::count() {
    return 0;
}

template<class T>
T *Stream<T>::max() {
    return nullptr;
}

template<class T>
T *Stream<T>::min() {
    return nullptr;
}

template<class T>
T *Stream<T>::firstFirst(bool (*)(const T *)) {
    return nullptr;
}

template<class T>
Stream<T> StreamFilter<T>::filter(std::function<bool(const T *)>) {
    return Stream<T>( );
}



