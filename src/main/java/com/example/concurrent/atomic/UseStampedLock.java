package com.example.concurrent.atomic;

import java.util.concurrent.locks.StampedLock;

/**
 * @author zhangming
 * @date 2018/12/22 11:08
 * <p>
 * {@link StampedLock}   实现乐观读
 */
public class UseStampedLock {

    private int size;
    private Object[] elements;
    private StampedLock lock = new StampedLock();

    public Object get(int n) {
        // 获取 stamp
        long stamp = lock.tryOptimisticRead();
        Object[] currentElements = elements;
        int currentSize = size;
        // 读取值并检查 stamp 是否有效（没有其他线程获得一个写锁），
        // 如果无效，你会获得一个读锁，他将会阻塞所有写锁，这就是乐观读，假设没有改变
        if (!lock.validate(stamp)) {
            // 阻塞
            stamp = lock.readLock();
            currentElements = elements;
            currentSize = size;
            lock.unlockRead(stamp);
        }

        return currentSize > n ? currentElements[n] : null;
    }
}
