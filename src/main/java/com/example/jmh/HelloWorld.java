package com.example.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author zhangming
 * @date 2019/4/14 14:14
 * <p>
 * benchmark 入门
 */
public class HelloWorld {

    @Benchmark
//    benchmark 的方法
    public void wellHelloThere() {

    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
//                可以使用正则
                .include(HelloWorld.class.getName())
//                使用几个线程测试
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
