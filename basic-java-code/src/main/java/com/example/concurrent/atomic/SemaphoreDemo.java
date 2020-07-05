package com.example.concurrent.atomic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author zhangming
 * @date 2019/3/9 10:04
 * <p>
 * Semaphore（信号量） 用来控制同时访问特定资源的线程数量
 * <p>
 * 用于流量控制，特别是公用资源有限的场景，比如数据库连接
 */
public class SemaphoreDemo {

    private static final Logger logger = LoggerFactory.getLogger(SemaphoreDemo.class);

    public static void main(String[] args) {
        final int THREAD_COUNT = 30;
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

        Semaphore semaphore = new Semaphore(10);
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(() -> {
                try {
                    semaphore.acquire();
                    logger.info("operate");
                    // 10个线程一批批执行
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
        }

        threadPool.shutdown();
    }
}
