package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * 利用字节码注入来实现代码覆盖工具，或者各式各样的 profiler。
 *
 * 运行时类。该类维护了一个 HashMap，用来统计每个类所新建实例的数目。当程序退出的时候，打印每个类的名字，以及其新建实例的数目。
 */
public class MyProfiler {

    public static ConcurrentHashMap<Class<?>, AtomicInteger> data = new ConcurrentHashMap<>();

    public static void fireAllocationEvent(Class<?> klass) {
        // mappingFunction K为参数，V为返回值
        data.computeIfAbsent(klass, kls -> new AtomicInteger()).incrementAndGet();
    }

    public static void dump() {
        data.forEach((kls, counter) -> {
            System.err.printf("%s: %d\n", kls.getName(), counter.get());
        });
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(MyProfiler::dump));
    }

}
