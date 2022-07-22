package com.example.concurrent.atomic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhangming
 * @date 2019/3/11 0:00
 * <p>
 * {@link ReentrantReadWriteLock} 读写锁示例
 * <p>
 * 读写分离场景  缓存
 */
public class CacheByReentrantReadWriteLock {

    static Map<String, Object> map = new HashMap<>();
    static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    static Lock rLock = readWriteLock.readLock();
    static Lock wLock = readWriteLock.writeLock();

    public static final Object get(String key) {
        // 保证每次写操作对所有的读写操作可见性
        rLock.lock();
        try {
            return map.get(key);
        } finally {
            rLock.unlock();
        }
    }

    public static final Object put(String key, Object value) {
        wLock.lock();
        try {
            return map.put(key, value);
        } finally {
            wLock.unlock();
        }
    }

    public static final void clear() {
        wLock.lock();
        try {
            map.clear();
        } finally {
            wLock.unlock();
        }
    }

}
