package com.example.concurrent.atomic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangming
 * @date 2019/3/11 22:48
 * <p>
 * {@link java.util.concurrent.locks.Condition} 与Lock配合实现等待/通知模式，类似 Object wait() notify()
 * <p>
 * {@link Condition} 方法调用之前必须要获取锁
 * <p>
 * 调用await()后，当前线程会释放锁并在此等待，其他线程调用 signal()后，唤醒一个等待在 Condition上的线程
 */
public class ConditionUseCase {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void conditionWait() throws InterruptedException {
        lock.lock();
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
    }

    public void conditionSignal() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
