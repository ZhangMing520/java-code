1. 在服务器端执行临时代码的三个问题：
- 如何编译提交到服务器的java代码？
- 如何执行编译之后的java代码？
- 如何收集java代码的执行结果？

> 第一个问题：一种方法是使用 tools.jar 包（在 sun jdk\lib 目录下）中的 com.sun.tools.javac.Main 类来编译 java 文件；
另一种是直接在客户端编译好，把字节码而不是java代码传到服务器端

> 第二个问题：要执行编译后的 java 代码，让类加载器加载这个类生成一个 Class 对象，然后反射调用一下某个方法就可以了（借用 “main()” 方法）

> 最后一个问题：一般是把程序往标准输出（System.out）和标准错误输出（System.err）中打印的信息收集起来。
如果重定向 out 和 err 到 PrintStream 流，会收集到其他进程的信息。直接在执行的类中把对 System.out 的符号引用替换为我们准备的 PrintStream 的符号引用。