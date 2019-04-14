package com.example.jvm;

/**
 * @author zhangming
 * @date 2019/3/13 11:19
 * <p>
 * 方法动态分派
 *
 * aload_  指令把刚刚创建的数据引用压到栈顶
 */
public class DynamicDispatch {

    static abstract class Human {
        protected abstract void sayHello();
    }

    static class Man extends Human {

        @Override
        protected void sayHello() {
            System.out.println("man say hello");
        }
    }

    static class Women extends Human {

        @Override
        protected void sayHello() {
            System.out.println("women say hello");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human women = new Women();

        man.sayHello();
        women.sayHello();

        man=new Women();
        man.sayHello();
    }
}
