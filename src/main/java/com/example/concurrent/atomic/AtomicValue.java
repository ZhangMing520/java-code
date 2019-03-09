package com.example.concurrent.atomic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author zhangming
 * @date 2018/12/20 23:56
 * <p>
 * 原子值
 */
public class AtomicValue {

    static final Logger logger = LoggerFactory.getLogger(AtomicValue.class);

    public static void main(String[] args) {
        final AtomicLong nextNumber = new AtomicLong();

        // 获取原值 - 增加1 - 设置值 - 返回值 ， 整个过程是原子的
        long id = nextNumber.incrementAndGet();

        // 此过程不是原子的  错误
        nextNumber.set(Math.max(nextNumber.get(), 100));

        long oldValue;
        long newValue;
        do {
            oldValue = nextNumber.get();
            newValue = Math.max(oldValue, 100);
        } while (!nextNumber.compareAndSet(oldValue, newValue));

        // java8 写法 不用写循环  实际上它帮你写了而已
        nextNumber.updateAndGet(x -> Math.max(x, 100));
        nextNumber.accumulateAndGet(100, Math::max);


//        LongAdder 效率比 AtomicLong 更高，内部是多个被加数，线程增加被加数也增加，最后计算总和值
//        LongAccumulator 和LongAdder思想是一样的，只是需要额外提供操作类型，比如 Long::sum
    }
}
