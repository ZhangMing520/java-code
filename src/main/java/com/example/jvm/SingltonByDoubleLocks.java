package com.example.jvm;

/**
 * @author zhangming
 * @date 2019/3/16 16:21
 * <p>
 * 双重检查锁
 */
public class SingltonByDoubleLocks {

    /**
     * volatile 保证了创建对象的过程不重排序   分配内存 -> 对象初始化 -> instance指向内存
     * <p>
     * 没有 volatile ，其他线程可能获取到没有初始化的对象
     */
    private static volatile Instance instance;

    public static Instance getInstance() {
        if (instance == null) {
            synchronized (SingltonByDoubleLocks.class) {
                if (instance == null) {
                    instance = new Instance();
                }
            }
        }
        return instance;
    }

}
