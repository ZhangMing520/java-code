package com.example.concurrent.atomic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangming
 * @date 2019/3/9 10:16
 * <p>
 * Exchanger（交换者）线程之间协作的工具类。进行线程间的数据交换，在同步点，两个吸纳城交换数据
 * 先执行exchange()方法的线程会等待
 * <p>
 * 主要用于遗传算法，或者校对工作
 */
public class ExchangerDemo {

    private static final Logger logger = LoggerFactory.getLogger(ExchangerDemo.class);

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        // 仅仅交换了线程名称数据
        threadPool.execute(() -> {
            try {
                String exchangeData = exchanger.exchange(Thread.currentThread().getName());
                logger.info("receive {}", exchangeData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            try {
                String exchangeData = exchanger.exchange(Thread.currentThread().getName());
                logger.info("receive {}", exchangeData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.shutdown();
    }

}
