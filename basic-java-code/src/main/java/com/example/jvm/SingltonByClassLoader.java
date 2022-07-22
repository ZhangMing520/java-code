package com.example.jvm;


/**
 * @author zhangming
 * @date 2019/3/16 16:09
 * <p>
 * 在执行类的初始化期间，jvm 会去获取一个锁。可以同步多个线程对同一个类的初始化。
 */
public class SingltonByClassLoader {

    private static class InstanceHolder {
        public static Instance instance = new Instance();
    }

    public static Instance getInstance() {
        // 导致类初始化
        return InstanceHolder.instance;
    }
}
