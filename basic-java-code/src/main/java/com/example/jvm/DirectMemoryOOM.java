package com.example.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author zhangming
 * @date 2019/3/10 20:54
 * <p>
 * -Xmx20M -XX:MaxDirectMemorySize=10M
 *
 * 此程序通过反射获取Unsafe实例并进行内存分配（Unsafe  getUnsafe()只有引导类加载器才能返回实例， rt.jar中的类才能使用）
 *
 * 真正申请分配内存的方法是 unsafe allocateMemory
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
