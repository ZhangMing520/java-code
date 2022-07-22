package com.example.load_class;

/**
 * @author zhangming
 * @date 2019/3/12 15:44
 */
public class ConstClass {

    static {
        System.out.println("ConstClass init");
    }

    public static final String HELLO_WORLD = "hello world";
}
