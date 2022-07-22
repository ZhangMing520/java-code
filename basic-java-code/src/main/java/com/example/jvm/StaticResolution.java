package com.example.jvm;

/**
 * @author zhangming
 * @date 2019/3/13 10:15
 * <p>
 * 方法静态解析
 * javap -verbose StaticResolution.class
 * <p>
 * invokestatic
 */
public class StaticResolution {

    public static void sayHello() {
        System.out.println("hello world");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }
}
