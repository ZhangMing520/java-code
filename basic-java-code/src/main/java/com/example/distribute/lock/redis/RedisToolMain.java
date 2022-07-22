package com.example.distribute.lock.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;


/**
 * @author zhangming
 * @date 2019/5/2 16:57
 */
public class RedisToolMain {
    public static void main(String[] args) {
        Config config =new Config();
//        Default is 30000 milliseconds  客户端崩溃时候，释放锁的时间
        config.setLockWatchdogTimeout(10000);

        RedissonClient client = Redisson.create(config);
        RLock lock = client.getLock("anyLock");
    }
}
