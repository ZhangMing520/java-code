1. Slice 语言所定义的基本数据类型

| 类型 | 定义及范围 | 长度 |
| --- | --- | --- |
| bool | false or true | >=1bit |
| byte | -128-123 or 0-255 | >=8bit |
| short | -2^15 to 2^15-1 | >=16bit |
| int | -2^31 to 2^31-1 | >=32bit |
| long | -2^63 to 2^63-1 | >=64bit |
| float | IEEE | >=32bit |
| double | IEEE | >=64bit |
| string | all unicode characters, excluding the character with all bits zero | variable-length |

> 由于 byte 在java里面是无符号的，范围是-128-127，因此不建议将byte用作数值型参数，而只用作原始字节类型传递二进制数据

2. Slice 中也可以直接定义常量
```C++
const bool AppendByDefault = true;
const byte LowerNibble = 0x0f;
const string Advice = "Don't Panic!";
const short TheAnswer = 42;
const double PI = 3.1416;

# 等价于 int 
enum Fruit{ Apple, Pear, Orange}
const Fruit FavoriteFruit = Pear
```

3. Slice 语言所定义的复合数据结构

|类型|含义说明|
| --- | ---|
| enum |枚举类型 enum Fruit{ Apple, Pear, Orange } 或 enum Fruit{ Apple=5, Pear=3, Orange=1 } 实际等价于 int|
| struct | 类似 JavaBean struct TimeOfDay{ short hour ; short minute ; short second }  不能嵌套 ；  字段可以定义默认值 |
| Sequence | 集合类型 支持基本类型的结合和复合类型的集合 sequence<Fruit> FruitPlatter; sequence<FruitPlatter> FruitBanquet; 集合的集合 |
| dictionary | Map 类型 类似于 HashMap  dictionary<long,Employee> EmployeeMap ； key类型是有限制的，只能是“等价”于整数类型的数据类型，
包括byte,short,int,long,bool,string及内部元素是“等价”整数类型的struct类型等 |

```C
// 不合法
struct TwoPoints{
    struct Point{  
        short x;
        short y;
    };
    Point coord1;
    Point coord2;
}

// 合法
struct Point{ 
    short x;
    short y;
};

struct TwoPoints{  
    Point coord1;
    Point coord2;
}

// 默认值
struct Location{
    string name;
    Point pt;
    bool display = true;
    string source = "GPS";
}

```