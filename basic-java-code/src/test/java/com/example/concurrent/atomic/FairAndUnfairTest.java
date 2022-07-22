package com.example.concurrent.atomic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangming
 * @date 2019/3/11 21:33
 * <p>
 * 公平锁和非公平锁
 */
public class FairAndUnfairTest {

    private static ReentrantLock2 fairLock = new ReentrantLock2(true);
    private static ReentrantLock2 unfairLock = new ReentrantLock2(false);

    @Test
    public void testFair() throws InterruptedException {
        testLock(fairLock);
    }

    @Test
    public void testUnfair() throws InterruptedException {
        testLock(unfairLock);
    }

    public void testLock(ReentrantLock2 lock) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Job(lock).start();
        }

        Thread.currentThread().join();
    }

    static class Job extends Thread {
        final ReentrantLock2 lock;

        public Job(ReentrantLock2 lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(lock.getQueuedThreads());
            } finally {
                lock.unlock();
            }
        }
    }

    static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        /**
         * 按照时间顺序输出
         *
         * @return
         */
        @Override
        protected Collection<Thread> getQueuedThreads() {
            List<Thread> list = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(list);
            return list;
        }
    }
}
