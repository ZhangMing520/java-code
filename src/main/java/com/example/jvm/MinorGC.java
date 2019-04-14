package com.example.jvm;

/**
 * @author zhangming
 * @date 2019/3/10 22:19
 * <p>
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 * <p>
 * 新生代GC（minor gc）:指发生在新生代的垃圾收集动作，此java对象大多朝生夕灭，所以minor gc非常频繁，一般回收速度比较快
 * 老年代GC（full gc）:指发生在老年代的GC，出现 major gc ，经常伴随至少一次minor gc。major gc 比minor gc 慢10倍以上
 *
 * 大对象直接进入老年代，大对象是指大量连续内存空间的java对象，比较典型的是长的字符串及数组
 *
 *  大于此值对象在老年代中分配
 * -XX:PretenureSizeThreshold=
 */
public class MinorGC {

    private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testAllocation();
    }

}
