1. 单一职责原则（Single Responsibility Principle）

> There should never be more than one reason for a class to change.

- 定义：应该有且仅有一个原因引起类的变更。
- 难点：对职责的定义，什么是类的职责，以及怎么划分类的职责。
- 单一职责适用于接口、类，同时也适用于方法。
- 对于单一职责原则，我的建议是接口一定要做到单一职责，类的设计尽量做到只有一个原因引起变化。类的单一职责确实会受非常多的因素制约。