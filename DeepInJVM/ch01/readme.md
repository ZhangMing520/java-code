1. Java虚拟机运行Java字节码

> 执行 Java 代码首先需要将它编译而成的 class 文件加载到 Java 虚拟机中。加载后的 Java 类会被存放于方法区（Method Area）中。实际运行时，虚拟机会执行方法区内的代码


2. 栈帧

> 在运行过程中，每当调用进入一个 Java 方法，Java 虚拟机会在当前线程的 Java 方法栈中生成一个栈帧，用以存放局部变量以及字节码的操作数。这个栈帧的大小是提前计算好的，而且 Java 虚拟机不要栈帧在内存空间里连续分布。

> 当退出当前执行的方法时，不管是正常返回还是异常返回，Java 虚拟机均会弹出当前线程的当前栈帧，并将之舍弃。


3. 编译字节码形式

> 第一种是解释执行，即逐条将字节码翻译成机器码并执行
> 第二种是即时编译（Just-In-Time compilation, JIT），即将一个方法中包含的所有字节码编译成机器码后再执行。
