////
//// Created by ddemikho on 20/06/2021.
////
//
//#include "Stream.h"
//
////template<class T>
////Stream<T>::Stream(std::function<std::vector<T*>()> function) {
////    lambda = function;
////}
//
//
//
//template<class T>
//template<template<typename,typename> class TContainer, class U>
//Stream<T> Stream<T>::of(TContainer<T*, U> container) {
//
//    return Stream<T>([container]() {
//        std::vector<T*> myContainer;
//        std::copy(container.begin(), container.end(), myContainer.begin());
//        return myContainer;
//    });
//}
//
//template<class T>
//template<class S>
//Stream<T> Stream<T>::of(std::map<S, T*> map) {
//    return Stream<T>([map]() {
//        std::vector<T*> values;
//        for(auto i : map) {
//            values.push_back(i.second);
//        }
//        return values;});
//}
//
//template<class T>
//Stream<T> Stream<T>::filter(std::function<bool(const T *)> pred) {
//    auto collection = lambda();
//    return Stream<T>([pred, collection]() {
//        auto newCollection = std::vector<T*>();
//        std::copy_if(collection.begin(), collection.end(), newCollection.begin(), pred);
//        return newCollection;
//    });
//}
//
//template<class T>
//template<class R>
//Stream<R> Stream<T>::map(std::function<R *(const T *)> mapFunc) {
//    auto collection = lambda();
//    return Stream<R>([mapFunc, collection](){
//        auto newCollection = std::vector<R*>();
//        std::transform(collection.being(), collection.end(), newCollection.begin(), mapFunc);
//        return newCollection;
//    });
//}
//
//template<class T>
//Stream<T> Stream<T>::distinct(std::function<bool(const T *, const T *)> cmp) {
//    auto collection = lambda();
//    return Stream<T>([cmp, collection](){
//        auto newCollection = std::vector<T*>();
//        std::unique_copy(collection.begin(), collection.end(), newCollection.begin(), cmp);
//        return newCollection;
//    });
//}
//
//template<class T>
//Stream<T> Stream<T>::distinct() {
//    auto collection = lambda();
//    return Stream<T>([collection](){
//        auto newCollection = std::vector<T*>();
//        std::unique_copy(collection.begin(), collection.end(), newCollection.begin());
//        return newCollection;
//    });
//}
//
//template<class T>
//Stream<T> Stream<T>::sorted() {
//    auto collection = lambda();
//    return Stream<T>([collection]() {
//        auto newCollection = std::vector<T*>();
//        std::copy(collection.begin(), collection.end(), newCollection.begin());
//        std::sort(newCollection.begin(), newCollection.end());
//        return newCollection;
//    });
//}
//
//template<class T>
//template<template<typename > class TContainer>
//TContainer<T*> Stream<T>::collect() {
//    auto collection = lambda();
//    TContainer<T*> newCollection;
//    std::copy(collection.begin(), collection.end(), newCollection.begin());
//    return newCollection;
//
//}
//
//template<class T>
//void Stream<T>::forEach(std::function<void(T *)> action) {
//    auto collection = lambda();
//    std::for_each(collection.begin(), collection.end(), action);
//}
//
//template<class T>
//T* Stream<T>::reduce(T *, std::function<T *(const T *, const T *)>) {
//    return nullptr;
//}
//
//template<class T>
//bool Stream<T>::anyMatch(std::function<bool(const T*)> pred) {
//    auto collection = lambda();
//    return std::any_of(collection.begin(), collection.end(), pred);
//}
//
//template<class T>
//bool Stream<T>::allMatch(std::function<bool(const T*)> pred) {
//    auto collection = lambda();
//    return std::all_of(collection.begin(), collection.end(), pred);
//}
//
//template<class T>
//int Stream<T>::count() {
//    auto collection = lambda();
//    return std::count(collection.begin(), collection.end());
//}
//
//template<class T>
//T *Stream<T>::max() {
//    auto collection = lambda();
//    return std::max(collection.begin(), collection.end());
//}
//
//template<class T>
//T *Stream<T>::min() {
//    auto collection = lambda();
//    return std::min(collection.begin(), collection.end());
//}
//
//template<class T>
//T *Stream<T>::findFirst(std::function<bool(const T*)> pred) {
//    auto collection = lambda();
//    return *std::find(collection.begin(), collection.end(), pred);
//}
//
//
//
//
//
////template<class T>
////Stream<T> StreamFilter<T>::filter(std::function<bool(const T *)>) {
////    return Stream<T>( );
////}
////
////template<class T>
////template<class U>
////StreamFilter<T>::StreamFilter(Stream<U>) {
////
////}
//
//
//
