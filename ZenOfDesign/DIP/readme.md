1. 依赖倒置原则（Dependence Inversion Principle）

> High level modules should not depend upon low level modules. Both should depend upon abstractions.
> Abstractions should not depend upon details. Details should depend upon abstractions.

- 高层模块不应该依赖低层次模块，两者都应该依赖其抽象
- 抽象不应该依赖细节
- 细节应该依赖抽象


2. 依赖倒置原则在Java语言总的表现

> 在Java语言中，抽象就是值接口或抽象类，两者都是不能直接被实例化的；细节就是实现类，实现接口或继承抽象类而产生的类就是细节，其特点就是可以直接被实例化。

> 更加精简的定义就是“面向接口编程”

> 在Java中，一个变量可以有两种类型：表面类型和实际类型，表面类型是在定义的时候赋予的类型，实际类型是对象的类型。

- 模块间的依赖通过抽象发生，实现类之间不发生直接的依赖关系，其依赖关系是通过接口或者抽象类产生的；
- 接口或者抽象类不依赖于实现类
- 实现类依赖接口或者抽象类

3. 对象的依赖关系有三种方式来传递

- 构造函数传递依赖对象

```java
public interface IDriver {
    void drive();
}

public class Driver implements IDriver {
    private Icar car;

    public Driver(Icar car) {
        this.car = car;
    }

    public void drive() {
        this.car.drive();
    }
}
```

- Setter方法传递依赖对象

```java
public interface IDriver {

    void setCar(Icar car);

    void drive();
}

public class Driver implements IDriver {
    private Icar car;

    public void setCar(Icar car) {
        this.car = car;
    }

    public void drive() {
        this.car.drive();
    }
}
```

- 接口声明依赖对象（在接口的方法中声明依赖对象），也叫接口注入

```java
public interface IDriver {
    void drive(Icar car);
}

public class Driver implements IDriver {
    public void drive(Icar car) {
        car.drive();
    }
}

```

4. 依赖倒置最佳实践

- 每个类尽量都有接口或者抽象类，或者抽象类和接口两者都具备
- 变量的表面类型尽量是接口或者抽象类
- 任何类都不应该从具体类派生（负责项目维护的同志，基本上可以不考虑这个规则，为什么？维护工作基本都是进行扩展开发，修复行为， 通过一个继承关系，覆写一个方法就可以修正一个很大的BUG，何必去继承最高的基类呢？
  当然这种情况尽量发生在不甚了解父类或者无法获得父类代码的情况下。）
- 尽量不要覆写基类的方法
- 结合里式替换原则使用（父类出现的地方子类就能出现）