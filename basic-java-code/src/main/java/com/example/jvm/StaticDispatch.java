package com.example.jvm;

/**
 * @author zhangming
 * @date 2019/3/13 10:56
 * <p>
 * 方法静态分派（method overload resolution）
 * <p>
 * 在编译阶段，javac 编译器就根据参数的静态类型决定使用哪一个重载版本
 * <p>
 * 所有依赖静态类型来定位方法执行版本的分派动作，都称为静态分派
 *
 * 基本类型在静态分配是会发生自动类型转换，或者自动装箱
 *
 * char -> int -> long  ->  float  ->  double
 */
public class StaticDispatch {

    static abstract class Human {
    }

    static class Man extends Human {
    }

    static class Women extends Human {
    }

    public void sayHello(Human guy) {
        System.out.println("hello,guy");
    }

    public void sayHello(Man guy) {
        System.out.println("hello,gentleman");
    }

    public void sayHello(Women guy) {
        System.out.println("hello,lay");
    }


    public static void main(String[] args) {
        Human man = new Man();
        Human women = new Women();

        StaticDispatch sd = new StaticDispatch();
//        hello,guy
//        hello,guy
        sd.sayHello(man);
        sd.sayHello(women);

    }
}
