
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


    std::function<std::vector<T*>()> lambda;
public:

    explicit Stream(std::function<std::vector<T*>()> function) {
        lambda = function;
    }

    template<template<typename,typename> class TContainer, class U>
    static Stream<T> of(TContainer<T*,U> container) {

        return Stream<T>([container]() {
            auto myContainer = std::vector<T*>();
            for (auto i : container) {
                myContainer.push_back(i);
            }
            return myContainer;
        });
    }

    template<class S>
    static Stream<T> of(std::map<S,T *> map) {
        return Stream<T>([map]() {
            auto values = std::vector<T*>();
            for(auto i : map) {
                values.push_back(i.second);
            }
            return values;});
    }

    Stream<T> filter(std::function<bool(const T *)> pred) {
        auto collection = lambda();
        return Stream<T>([pred, collection]() {
            auto newCollection = std::vector<T*>();
        for(auto i : collection) {
            if(pred(i)) {
                newCollection.push_back(i);
            }
        }
            return newCollection;
        });
    }

    template<class R>
    Stream<R> map(std::function<R*(const T *)> mapFunc) {
        auto collection = lambda();
        return Stream<R>([mapFunc, collection](){
            auto newCollection = std::vector<R*>();
            for(auto i : collection) {
                newCollection.push_back(mapFunc(i));
            }
            return newCollection;
        });
    }

    Stream<T> distinct(std::function<bool(const T *, const T *)> cmp) {
        auto collection = lambda();
        return Stream<T>([cmp, collection](){
            if(collection.size() == 0) {
                return std::vector<T*>();
            }
            auto newCollection = std::vector<T*>(collection.size());
            auto it = std::unique_copy(collection.begin(), collection.end(), newCollection.begin(), cmp);
            newCollection.resize(std::distance(newCollection.begin(), it));
            return newCollection;
        });
    }

    Stream<T> distinct() {
        auto collection = lambda();
        return Stream<T>([collection](){
            if(collection.size() == 0) {
                return std::vector<T*>();
            }
            auto newCollection = std::vector<T*>(collection.size());
            auto it = std::unique_copy(collection.begin(), collection.end(), newCollection.begin(), [](const T* e1, const T* e2) -> bool {
                return *e1 == *e2;
            });
            newCollection.resize(std::distance(newCollection.begin(), it));
            return newCollection;
        });
    }

    Stream<T> sorted() {
        auto collection = lambda();
        return Stream<T>([collection]() {
            auto newCollection = std::vector<T*>();
            if(collection.size() == 0) return newCollection;
            for(auto i : collection) {
                newCollection.push_back(i);
            }
            std::sort(newCollection.begin(), newCollection.end(), []( T* e1,  T* e2) -> bool{
                return *e1 < *e2;
            });
            return newCollection;
        });
    }

    template<class TContainer>
    TContainer collect() {
        auto collection = lambda();
        TContainer newCollection;
        auto it = newCollection.begin();
        for(auto i : collection) {
            newCollection.insert(newCollection.end(), i);
        }
        return newCollection;

    }

    void forEach(std::function<void(T *)> action) {
        auto collection = lambda();
        if(collection.size() == 0) return;
        std::for_each(collection.begin(), collection.end(), action);
    }

    T* reduce(T * initial, std::function<T*(const T *, const T *)> redFunc) {
        auto collection = lambda();
        T* res = initial;
        for (auto i : collection) {
            res = redFunc(res, i);
        }
        return res;
    }

    T* min() {
        auto collection = lambda();
        if(!collection.size()) return nullptr;
        T* min = collection[0];
        for (int i = 1; i < collection.size(); i++) {
            if (*collection[i] < *min) {
                min = collection[i];
            }
        }
        return min;
    }

    T* max() {
        auto collection = lambda();
        if(!collection.size()) return nullptr;
        T* max = collection[0];
        for (int i = 1; i < collection.size(); i++) {
            if (*max < *collection[i]) {
                max = collection[i];
            }
        }
        return max;
    }

    int count() {
        return lambda().size();
    }

    bool allMatch(std::function<bool(const T*)> pred) {
        auto collection = lambda();
        if(!collection.size()) return true;
        return std::all_of(collection.begin(), collection.end(), pred);
    }

    bool anyMatch(std::function<bool(const T*)> pred) {
        auto collection = lambda();
        if(!collection.size()) return false;
        return std::any_of(collection.begin(), collection.end(), pred);
    }

    T* findFirst(std::function<bool(const T*)> pred) {
        auto collection = lambda();
        for(int i = 0; i < collection.size(); i++) {
            if(pred(collection[i])) return collection[i];
        }
        return nullptr;
    }
};



#endif //HW5_STREAM_H
