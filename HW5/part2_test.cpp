#include <iostream>
#include <vector>
#include <cassert>
#include "Stream.h"

template <typename T>
struct Cell {
    T value;

    Cell(const T value) : value(value) {}

    bool operator==(const Cell<T>& other) const {
        return other.value == value;
    }

    void print() {
        std::cout << "Cell: " << value << std::endl;
    }
};

template <typename T>
bool compareValues(std::vector<T*> vPointer, std::vector<T> v) {
    for(int i = 0 ; i < vPointer.size() ; i++){
        if(*vPointer[i] != v[i]){
            return false;
        }
    }
    return true;
}

int main() {
    int array[10] = { 1, 2, 2, 3, 4, 6, 5, 7, 8, 9 };
    std::vector<int*> vector;
    for(int i = 0 ; i < 10 ; i++) vector.push_back(array + i);

    assert(Stream<int>::of(vector).filter([](const int* val) { return *val != 2; } ).count() == 8);
    assert(Stream<int>::of(vector).distinct().count() == 9);

    std::vector<int> other = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    assert(compareValues(Stream<int>::of(vector).distinct().sorted().collect<std::vector<int*>>(), other));

    assert(Stream<int>::of(vector).map<Cell<int>>([](const int* a) { return new Cell<int>(*a); }).distinct().count() == 9);

    int initial = 0;
    assert(*Stream<int>::of(vector).reduce(&initial, [](const int* a, const int* b) { auto * c = new int; *c = *a + *b; return c; }) == 47);
    int arr2[10] = {8, 4, 9, 2, 7, 6, 3, 5, 10, 1};
    std::vector<int*> vector2;
    for(int i = 0 ; i < 10 ; i++) vector2.push_back(arr2 + i);
    assert(*Stream<int>::of(vector2).min() == 1);
    assert(*Stream<int>::of(vector2).max() == 10);
    assert(Stream<int>::of(vector2).allMatch([](const int* x) -> bool {return *x > 0;}));
    assert(Stream<int>::of(vector2).anyMatch([](const int* x) -> bool {return *x == 1;}));
    assert(Stream<int>::of(vector2).findFirst([](const int* x) -> bool {return *x < 0;}) == nullptr);
    return 0;
}

