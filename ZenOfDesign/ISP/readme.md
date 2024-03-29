1. 接口分为两种

- 实例接口（Object Interface），在Java中声明一个类，然后用new关键字产生一个实例，它是对一个类型的事物的描述，这是一个接口
- 类接口（Class Interface），Java中经常使用的interface关键字定义的接口。

2. 隔离两种定义

- Clients should not be forced to depend upon interfaces that they don't use. （客户端不应该依赖它不需要的接口）
- The dependency of one class to another one should depend on the smallest possible interface. （类间的依赖关系应该建立在最小的接口上。）
- 两句定义概括为一句话：建立单一接口，不要建立臃肿庞大的接口。接口尽量细化，同时接口中的方法尽量少。

3. 接口隔离原则与单一职责原则不是相同的么？

> 错，接口隔离原则与单一职责的审视角度是不相同的，单一职责要求的是类和接口职责单一，注重的是职责，这是业务逻辑上的划分，而接口隔离原则要求接口的方法尽量少。
> 例如一个接口的职责可能包含10个方法，这10方法都放在一个接口中，并且提供给多个模块访问，各个模块按照规定的权限来访问，在系统外通过文档约束“不使用的方法不要访问”，
> 按照单一职责原则是允许的，按照接口隔离原则是不允许的，因为它要求“尽量使用多个专门的接口”。
> 专门的接口指什么？就是指提供给每个模块的都应该是单一接口，提供给几个模块就应该有几个接口，而不是建立一个庞大的臃肿的接口，容纳所有的客户端访问。

4. 保证接口的纯洁性

- 接口要尽量小（根据接口隔离原则拆分接口时候，首先必须要满足单一职责原则）
- 接口要高内聚（要求在接口中尽量少公布public方法，接口是对外的承诺，承诺越少对系统的开发越有利，变更的风险也就越少）
- 定制服务（只提供访问者需要的方法）
- 接口设计是有限度的（接口的设计粒度不是越小越好）