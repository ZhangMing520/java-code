package com.example.concurrent.atomic;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author zhangming
 * @date 2019/3/11 17:01
 *
 * {@link AbstractQueuedSynchronizer } 同步队列
 *
 * <p>
 * 独占锁（同一时刻只能有一个线程获取到锁）
 */
public class Mutex implements Lock {

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mutex mutex = (Mutex) o;
        return Objects.equals(sync, mutex.sync);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sync);
    }

    public boolean tryLock(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return sync.tryAcquireNanos(1, timeUnit.toNanos(timeout));
    }


    private static final class Sync extends AbstractQueuedSynchronizer {

        /**
         * 是否被当前线程独占
         *
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 状态是 0 的时候获取锁，状态是 1 表示获取了同步状态
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;

        }

        /**
         * 释放锁，将状态设置为0
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }

            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 返回一个 {@link Condition}，，每个condition都包含了一个condition队列
         *
         * @return
         */
        Condition newCondition() {
            return new ConditionObject();
        }
    }
}
