package com.example.concurrent.atomic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

/**
 * @author zhangming
 * @date 2019/3/11 18:06
 */
public class TwinsLockTest {

    private static final Logger logger = LoggerFactory.getLogger(TwinsLockTest.class);

    @Test
    public void test() throws InterruptedException {
        final TwinsLock lock = new TwinsLock();

        class Worker extends Thread {
            @Override
            public void run() {
                lock.lock();
                try {
                    logger.info(Thread.currentThread().getName());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.start();
        }

        Thread.currentThread().join();
    }

}