package com.example.concurrent.atomic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangming
 * @date 2019/3/11 22:58
 * <p>
 * 有界队列
 * 队列为空时，队列获取会被阻塞，队列已满时候，插入操作会被阻塞
 */
public class BoundedQueue<T> {
    private Object[] items;

    private int addIndex, removeIndex, count;

    private Lock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BoundedQueue(int size) {
        items = new Object[size];
    }

    public void add(T t) throws InterruptedException {
        lock.lock();
        try {
            // while 防止过早或意外的通知  而不是使用 if
            while (count == items.length)
                notFull.await();

            items[addIndex] = t;
            if (++addIndex == items.length) {
                addIndex = 0;
            }

            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T remove() throws InterruptedException {
        lock.lock();

        try {
            while (count == 0)
                notEmpty.await();

            Object x = items[removeIndex];
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            --count;
            notFull.signal();
            return (T) x;
        } finally {
            lock.unlock();
        }
    }
}
