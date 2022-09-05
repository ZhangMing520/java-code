1. 开闭原则定义

> Software entities like classes, modules and functions should be open for extension but closed for modifications.
> （一个软件实体如类、模块和函数应该对扩展开放，对修改关闭）

> 一个软件实体应该通过扩展来实现变化，而不是通过修改已有的代码来实现变化。

> 开闭原则对扩展开放，对修改关闭，并不意味着不做任何修改，低层模块的变更，必然要有高层模块进行耦合，否则就是一个孤立无意义的代码片段。