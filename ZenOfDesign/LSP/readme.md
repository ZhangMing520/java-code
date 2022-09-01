1. 里式替换原则（Liskov Substitution Principle）

> If for object o1 of type S there is another o2 of type T such that for all programs P defined in terms of T,
> the behavior of P is unchanged when o1 is substituted for o2 then S is a subtype of T.

> 如果对每一个类型为S的对象o1，都有类型为T的对象o2，使得以T定义的所有程序P在所有对象o1都替换成o2时，程序P的行为没有发生变化，那么类型S是类型T的子类型。

> Functions that use pointers or references to base classes must be able to use objects of derived classes without knowing it.

> 所有引用基类的地方必须能透明的使用其子类的对象。

2. [plantuml类图](https://plantuml.com/zh/class-diagram)