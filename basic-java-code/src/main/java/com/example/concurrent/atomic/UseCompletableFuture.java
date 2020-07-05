package com.example.concurrent.atomic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.logging.Logger;


/**
 * @author zhangming
 * @date 2018/12/22 14:50
 * <p>
 * Async 结尾方法，要么是默认的 ForkJoinPool，要么提供 Executor
 * <p>
 * thenApply  thenApplySync 在同一个线程或者另一个线程中运行
 */
public class UseCompletableFuture {


    public static void main(String[] args) throws InterruptedException, IOException {
        Logger logger = Logger.getGlobal();

        byte[] bytes = {(byte) 0b10, (byte) 0b10};
        BitSet bitSet = BitSet.valueOf(bytes);
        logger.info(bitSet.toString());
    }
}
