package com.example.jvm;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author zhangming
 * @date 2019/3/13 18:05
 */
public class TestClass {

    static boolean boolValue;

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        Class<?> clazz = Class.forName("sun.misc.Unsafe");

        Field theUnsafe = clazz.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        TestClass foo=new TestClass();
        Field boolValue = TestClass.class.getDeclaredField("boolValue");
        boolValue.setAccessible(true);
        long staticFieldOffset = unsafe.staticFieldOffset(boolValue);


//        Unsafe.putBoolean会做掩码，另外方法返回也会对boolean byte char short进行掩码
        // 此处有疑问 为啥设置 2,3 没用呢
        unsafe.putByte(TestClass.class, staticFieldOffset, (byte)2); // 设置为: 2
        System.out.println(unsafe.getByte(TestClass.class, staticFieldOffset)); // 打印getByte: 2
        System.out.println(unsafe.getBoolean(TestClass.class, staticFieldOffset)); // 打印getBoolean: true

        unsafe.putByte(TestClass.class, staticFieldOffset, (byte)3); // 设置为: 3
        System.out.println(unsafe.getByte(TestClass.class, staticFieldOffset)); // 打印getByte: 3
        System.out.println(unsafe.getBoolean(TestClass.class, staticFieldOffset)); // 打印getBoolean: true

    }
}
