package com.example.concurrent.atomic;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhangming
 * @date 2019/3/11 22:16
 * <p>
 * {@link ReentrantReadWriteLock} 读写锁
 *
 * <p>
 * 锁降级：把持住（当前拥有的）写锁，再获取到读锁，随后释放先前拥有的写锁的过程
 */
public class ReentrantReadWriteLockDowngrade {

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    private final ReentrantReadWriteLock.ReadLock rLock = rwLock.readLock();
    private final ReentrantReadWriteLock.WriteLock wLock = rwLock.writeLock();

    private volatile boolean update = false;


    /**
     * 主要是分阶段使用锁，在写数据的时候使用写锁，在释放写锁之前获取读锁，这样其他的写锁就不会写数据了，但是锁降级为读锁，直到数据处理完毕
     * <p>
     * 外面的 rLock.lock() 主要是担心 update 已被修改，那么后面 rLock.unlock() 会抛出异常
     * <p>
     * 应用场景：写数据的过程较少或者只有一次，但是使用数据的过程比较多
     */
    public void processData() {
        rLock.lock();
        if (!update) {
            // 必须先释放读锁
            rLock.unlock();
            // 锁降级从写锁获取到开始
            wLock.lock();
            try {
                if (!update) {
                    // 准备数据阶段

                    update = true;
                }
                rLock.lock();
            } finally {
                wLock.unlock();
            }// 锁降级完成，写锁降级为读锁
        }

        try {
            // 使用数据阶段

        } finally {
            rLock.unlock();
        }

    }

}
