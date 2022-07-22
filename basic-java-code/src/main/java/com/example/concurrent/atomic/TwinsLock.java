package com.example.concurrent.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author zhangming
 * @date 2019/3/11 17:53
 * <p>
 * 基于 {@link AbstractQueuedSynchronizer } 同步队列，设计同步工具：同一时刻，只允许至多两个线程同时访问，
 * 超过两个线程的访问将被阻塞
 * 类似 {@link java.util.concurrent.Semaphore }
 * <p>
 * status 合法范围 0 1 2
 */
public class TwinsLock implements Lock {

    private final Sync sync = new Sync(2);

    public void lock() {
        sync.acquireShared(1);
    }

    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }


    static final class Sync extends AbstractQueuedSynchronizer {

        Sync(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("count must large than zero.");
            }

            setState(count);
        }

        /**
         * @param acquires
         * @return 返回值大于等于0时候，当前线程才获取同步状态
         */
        @Override
        protected int tryAcquireShared(int acquires) {
            for (;;) {
                int available = getState();
                int remaining = available - acquires;
                if (remaining < 0 ||
                        compareAndSetState(available, remaining))
                    return remaining;
            }
        }

        @Override
        protected boolean tryReleaseShared(int releases) {
            for (;;) {
                int current = getState();
                int next = current + releases;
                if (next < current) // overflow
                    throw new Error("Maximum permit count exceeded");
                if (compareAndSetState(current, next))
                    return true;
            }
        }
    }

}
