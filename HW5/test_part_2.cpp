#include <iostream>
#include <vector>
#include <cassert>
#include "Stream.h"
#include <map>
#include <list>
#include <string>
using namespace std;

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

struct Num {
    int val;

    explicit Num(int a) { this->val = a; }

    bool operator<(const Num& rhs) const
    {
        return this->val < rhs.val;
    }
};

struct Employee
{
public:
    int age;
    std::string name;  // Does not participate in comparisons
    Employee(int age, string name1) : age(age), name(name1) {}

    bool operator<(const Employee & rhs) const
    {
        return age < rhs.age;
    }

    bool operator!=(const Employee & rhs) const
    {
        return age != rhs.age ;
    }

    bool operator==(const Employee & rhs) const
    {
        return age == rhs.age ;
    }

    void print() const{
        std::cout << "Employee: " << name << std::endl;
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

template<typename T>
bool compareValuesList(std::list<T*> lPointer, std::vector<T> v) {
    int i = 0 ;
    for (const auto& x : lPointer) {
        if (i >= lPointer.size())
            break;

        if(*x != v[i]){
            return false;
        }

        i++;
    }
    return true;
}

bool compareNumsListValues(std::list<Num*> lPointer, std::vector<int> v) {
    int i = 0 ;
    for (const auto& x : lPointer) {
        if (i >= lPointer.size())
            break;

        if(x->val != v[i]){
            return false;
        }

        i++;
    }
    return true;
}


int main() {
    int array[10] = { 1, 2, 2, 3, 4, 6, 5, 7, 8, 9 };

    std::vector<int*> vector;
    for(int i = 0 ; i < 10 ; i++) vector.push_back(array + i);

    std::vector<int*> res = Stream<int>::of(vector).filter([](const int* val) { return *val != 2; } ).collect<std::vector<int*>>();
    assert(Stream<int>::of(vector).filter([](const int* val) { return *val != 2; } ).count() == 8);
    assert(Stream<int>::of(vector).distinct().count() == 9);

    std::vector<int> other = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    assert(compareValues(Stream<int>::of(vector).distinct().sorted().collect<std::vector<int*>>(), other));

    assert(Stream<int>::of(vector).map<Cell<int>>([](const int* a) { return new Cell<int>(*a); }).count() == 10);

    int initial = 0;
    assert(*Stream<int>::of(vector).reduce(&initial, [](const int* a, const int* b) { auto * c = new int; *c = *a + *b; return c; }) == 47);
    assert(*Stream<int>::of(vector).max() == 9);
    assert(*Stream<int>::of(vector).min() == 1);
    assert(Stream<int>::of(vector).distinct().sorted().filter([](const int* val) { return *val != 2; } ).count() == 8);
    assert(Stream<int>::of(vector).distinct().sorted().filter([](const int* val) { return *val == 2; } ).count() == 1);
    Stream<int>::of(vector).forEach([](const int* val) { cout << *val << endl ; });

    string array2[6] = {"hi","my","name","is","mr","robot"};
    std::vector<string*> vector2;
    for(int i = 0 ; i < 6 ; i++) vector2.push_back(array2 + i);
    assert(Stream<string>::of(vector2).distinct().count() == 6);

    std::vector<string> other2 = { "hi", "is", "mr" ,"my", "name","robot"};
    assert(compareValues(Stream<string>::of(vector2).sorted().collect<std::vector<string*>>(), other2));

    assert(Stream<string>::of(vector2).filter([](const string* val) { return *val != "robot"; }).count() == 5);
    Stream<string>::of(vector2).forEach([](const string* val) { cout << *val << endl; });

    assert(*Stream<string>::of(vector2).min() == "hi");
    assert(*Stream<string>::of(vector2).max() == "robot");
    assert(*Stream<string>::of(vector2).filter([](const string* val) { return *val != "robot"; }).min() == "hi");
    assert(*Stream<string>::of(vector2).filter([](const string* val) { return *val != "robot"; }).max() == "name");
    assert(Stream<string>::of(vector2).anyMatch([](const string* val) { return *val == "robot"; }) == true );
    assert(Stream<string>::of(vector2).anyMatch([](const string* val) { return *val == "hello"; }) == false );
    assert(*Stream<string>::of(vector2).findFirst([](const string* val) { return (*val).length()==2; }) == "hi" );
    assert(Stream<string>::of(vector2).findFirst([](const string* val) { return (*val).length()==3; }) == nullptr );
    assert(Stream<string>::of(vector2).filter([](const string* val) { return *val != "robot"; }).allMatch([](const string* val) { return *val != "robot"; }) == true);
    assert(Stream<string>::of(vector2).filter([](const string* val) { return *val != "robot"; }).allMatch([](const string* val) { return *val != "my"; }) == false);
    assert(Stream<string>::of(vector2).filter([](const string* val) { return *val != "robot"; }).anyMatch([](const string* val) { return *val == "robot"; }) == false);
    string initial2 = "Elliot:";
    assert(*Stream<string>::of(vector2).reduce(&initial2, [](const string * a, const string * b) { auto * c = new string ; *c = *a +" "+ *b; return c; }) == "Elliot: hi my name is mr robot");


    Employee em1 = {35, "Elliot"};
    Employee em2 = {32, "Darlin"};
    Employee em3 = {48, "Mr robot"};
    Employee em4 = {34, "Angella"};
    Employee em5 = {38, "Tairel"};

    std::vector<Employee*> employees;
    employees.push_back(&em1);
    employees.push_back(&em2);
    employees.push_back(&em3);
    employees.push_back(&em4);
    employees.push_back(&em5);
    assert(Stream<Employee>::of(employees).count() == 5);
    assert(Stream<Employee>::of(employees).filter([](const Employee* val) { return (val->age) > 40; }).count() == 1);
    std::vector<Employee> other3 = {
        {32, "Darlin"},{34, "Angella"},{35, "Elliot"},{38, "Tairel"},{48, "Mr robot"}
    };
    (*std::max_element(other3.begin(),other3.end())).print();
    assert(compareValues(Stream<Employee>::of(employees).sorted().collect<std::vector<Employee*>>(), other3));
    cout << "the min is: " ;
    Stream<Employee>::of(employees).min()->print(); //should be Darlin!!!!!
    cout << "the max is: " ;
    Stream<Employee>::of(employees).max()->print(); //should be MR ROBOT!!!!!
    Stream<Employee>::of(employees).forEach([](const Employee* val) { val->print(); });

    map<string, int*> collection = {{"four", array+4}, {"one", array}, {"seven", array+7}, {"nine", array+9}};
    std::vector<int*> map_to_stream = Stream<int>::of(collection).sorted().collect<std::vector<int*>>();
    for (auto i : map_to_stream)
        std::cout << *i << std::endl;

    map<string, Employee*> emp_map = {{em1.name, &em1}, {em2.name, &em2}, {em3.name, &em3}, {em4.name, &em4}, {em5.name, &em5}};
    std::vector<Num*> emp_map_to_stream = Stream<Employee>::of(emp_map).map<Num>([](const Employee* e) { return new Num(e->age); }).sorted().collect<std::vector<Num*>>();
    for (auto i : emp_map_to_stream)
        std::cout << i->val << std::endl;

    std::vector<int> expected = {5, 6, 7, 8, 9};
    res = Stream<int>::of(vector).filter([](const int* a) { return *a > 4;}).sorted().collect<std::vector<int*>>();
    assert(compareValues(res, expected));

    expected = {};
    res = Stream<int>::of(vector).filter([](const int* a) { return *a > 9;}).collect<std::vector<int*>>();
    assert(compareValues(res, expected));

    expected = {11, 14, 17, 19};
    auto nums = Stream<int>::of(collection).map<Num>([](const int* a) { return new Num(*a + 10); }).sorted().collect<std::list<Num*>>();
    assert(compareNumsListValues(nums, expected));

    int ones_arr[5] = {1, 1, 1, 1, 1};
    std::vector<int*> ones;
    for(int i = 0 ; i < 5 ; i++) ones.push_back(ones_arr + i);
    expected = {1};
    auto empty_list = Stream<int>::of(ones).distinct().collect<std::list<int*>>();
    assert(compareValuesList(empty_list, expected));

    Num* init = new Num(1000);
    Num* reduce_res = Stream<int>::of(vector).map<Num>([](const int* a) { return new Num(*a); })
                           .reduce(init, [](const Num* a, const Num*b) { Num* res = new Num(a->val + b->val); delete a; delete b; return res;});
    int sum = 1000;
    for (const auto& x : vector)
        sum += *x;

    assert(reduce_res->val == sum);

    std::vector<int*> empty_vec = {};
    reduce_res = Stream<int>::of(empty_vec).map<Num>([](const int* a) { return new Num(*a); })
            .reduce(init, [](const Num* a, const Num*b) { Num* res = new Num(a->val + b->val); delete a; delete b; return res;});
    assert(reduce_res->val == init->val);


    Num* max_res = Stream<int>::of(empty_vec).map<Num>([](const int* a) { return new Num(*a); }).max();
    assert(max_res == nullptr);
    max_res = Stream<int>::of(vector).map<Num>([](const int* a) { return new Num(*a); }).max();
    assert(max_res->val == 9);

    Num* min_res = Stream<int>::of(empty_vec).map<Num>([](const int* a) { return new Num(*a); }).min();
    assert(min_res == nullptr);
    min_res = Stream<int>::of(vector).map<Num>([](const int* a) { return new Num(*a); }).min();
    assert(min_res->val == 1);

    Num* find_first_res = Stream<int>::of(empty_vec).map<Num>([](const int* a) { return new Num(*a); })
                                                    .findFirst([](const Num* a) { return false; });
    assert(find_first_res == nullptr);
    find_first_res = Stream<int>::of(vector).map<Num>([](const int* a) { return new Num(*a); })
                                              .findFirst([](const Num* a) { return false; });
    assert(find_first_res == nullptr);


    return 0;
}
