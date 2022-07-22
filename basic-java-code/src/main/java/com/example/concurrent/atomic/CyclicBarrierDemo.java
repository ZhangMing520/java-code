package com.example.concurrent.atomic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zhangming
 * @date 2019/3/9 9:33
 * <p>
 * 可循环使用的屏障
 * 让一组线程到达一个屏障（同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续运行
 *
 * await 表示已经到达屏障了
 * java主线程会检测是否还有非守护线程运行，如果有整个程序是不会终止的
 *
 * CyclicBarrier(int parties, Runnable barrierAction)   barrierAction 屏障开门之后 首先执行，只执行一次
 *
 * rest() 终止计数器
 */
public class CyclicBarrierDemo {

    private static final Logger logger = LoggerFactory.getLogger(CyclicBarrierDemo.class);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        int threadNum = 2;
        CyclicBarrier barrier = new CyclicBarrier(threadNum,()-> System.out.println(3));

        new Thread(()->{
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(1);
        }).start();

        barrier.await();
        System.out.println(2);
    }

}
