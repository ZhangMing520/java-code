package com.example.thread.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @Author: zhangming
 * @Date:Create in 7/7/18 12:27 AM
 * @Description: java7  forkjoin 比普通的线程池更高效（任务窃取）
 */
public class CountTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2; //阈值

    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        //是否需要　fork
        boolean forkOrNot = (end - start) <= THRESHOLD;
        if (forkOrNot) {
            sum = IntStream.rangeClosed(start, end).sum();
        } else {
            // fork
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);

            // 执行子任务
            leftTask.fork();
            rightTask.fork();

            // 等待子任务完成　获取结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            // 合并子任务
            sum = leftResult + rightResult;
        }

        return sum;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(1, 4);

        ForkJoinTask<Integer> result = forkJoinPool.submit(countTask);

        Integer sum = result.get();
        System.out.println(sum);

    }
}
