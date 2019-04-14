package com.example.load_class;

/**
 * @author zhangming
 * @date 2019/3/12 15:21
 */
public class SuperClass {

    static {
        System.out.println("SuperClass init");
    }

    public static int value = 123;
}
