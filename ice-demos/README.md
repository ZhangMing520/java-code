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

4. Ice Object

   > 一个Ice Object可以有一个主要接口及多个其他接口（其他接口被称之为facet），与java对象可以继承多个接口的做法一致

   > Java在需要返回多个结果时会用到一种技术，即传入一个Map对象或者Java Bean作为参数，而在方法里面修改Map或者JavaBean属性，就是Out Parameter 模式。Slice也支持这种做法，参数增加 out 修饰符，即变为出参

   ```java
   void changeSleepPeriod(TimeOfDay startTime,TimeOfDay stopTime, out TimeOfDay prevStartTime,out TimeOfDay prevStopTime)
   ```

   

5. Idempotent 关键字

   > 在Slice接口方法上增加 Idempotent 关键字，表示该方法是幂等的，即调用1次和调用2次其结果都是一样的。比如常见的查询操作基本上都是幂等的，而update和create等方法则不是，若一个方式是幂等的，则增加idempotent修饰后，可以让ice更好的实现“自动恢复错误”机制，即在某个 ice object 调用失败的情况下，无法区分是否已经调用过，但在因为网络错误导致没有正确返回结果的情况下，ice会再次调用idempotent修饰的方法，透明恢复故障

6. 多个模块的接口引用共同的数据对象

   ```c
   #include common.slice
   ```

7. slice 文件

   ```C
   // demo 表示模块名 和 Java package对应
   // 生成的java代码package对应 com.my.demo,则在Slice文件中增加以下注解
   [["java:package:com.my.demo"]]
   module demo{  
   }
   ```

   8. IceBox

      > IceBox 就好像一个Tomcat，我们只要写N个Ice服务的代码，用一个装配文件定义需要加载的服务列表、服务的启动参数、启动次序等必要信息，然后启动IceBox，我们党的应用系统就能够正常运行了。IceBox 内部会创建一个service manager对象（Ice.Admin）,这个对象专门负责loading和initializing那些配置好的服务，你可以有选择性地将这个对象暴露给远程的客户端，这样IceBox就可以执行某些远程管理任务

- void start(String name , Communicator communicator , String[] args); 服务启动方法，服务可以在start操作中初始化自身；这通常包括创建对象适配器和servant。name和args参数提供了来自服务的配置信息，communicator 参数是服务管理器为供服务使用而创建的Ice.Communicator 对象。取决于服务的配置，这个通信器实例会由同一个IceBox服务器中的其他服务共享，因此，你需要注意确保向对象适配器（ObjectAdapter）这样的对象的名字是唯一的

- void stop();服务停止方法，在stop方法中回收所有被service使用的资源，一般在stop中service会是一个object adapter对象无效（deactivates），不过也有可能对object adapter执行一个waitForDeactivate方法来确保所有未完成的请求在资源清理前得到处理，默认会将它的object adapter作为销毁 communicator 的一部分连同 communicator对象一起 destroy 掉，但在某些情况下不能做到，如IceBox中services使用共享的communicator对象时，你需要明确指明销毁它的 object adapter 对象

9. Ice为每个服务都创建了独立的线程池

   > 每个不同的服务是由不同的线程池的，相互隔离，防止一个不良设计和实现的服务影响到其他服务，从而导致系统稳定性降低（Zeroc Ice 权威指南）

> 与书上内容有出入，实践是相同线程，线程id也是一样的；可能后面优化修改了

10. Ice Registry

    > 服务注册表Registry组件，它是一个以二进制文件形式存储运行期Ice服务注册信息的独立进程，支持主从同步，从节点可以分担查询请求，类似MySQL读写分离的功能，并防止单点故障，同时依托Registry的功能，ZeroC设计实现了Service Locator服务组件，它是一个标准的Ice Objec服务对象，可以在Ice程序中调用这个服务，从而解决服务地址的查询问题。另外，Service Locator服务组件和Ice 客户端 Runtime 框架相互结合，实现了自动化的透明的服务调用负载均衡功能

- 自动实现多种可选择的负载均衡算法，客户端无需自己实现
- 服务的部署位置和部署数量发生变化后，客户端无需重启、自动感知和适应