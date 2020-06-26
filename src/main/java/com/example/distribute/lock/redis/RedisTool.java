package com.example.distribute.lock.redis;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * @author zhangming
 * @date 2019/5/2 16:27
 *
 * <a href="http://wudashan.cn/2017/10/23/Redis-Distributed-Lock-Implement/#releaseLock-wrongDemo2">redis lua脚本官方解释</a>
 *
 * <a href="https://redis.io/commands/eval">eval 命令</a>
 *
 * <a href="https://github.com/redisson/redisson">redission 官方推荐实现</a>
 * <p>
 * requestId 可以换成 threadId
 */
public class RedisTool {

    /**
     * lock
     *
     * jedis 内置常量
     */
    private static final String LOCK_SUCCESS = "OK";
    //    key 不存在设置成功
    private static final String SET_IF_NOT_EXIST = "NX";
    //  设置一个过期时间
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * unlock
     */
    private static final Long UNLOCK_SUCCESS = 1L;

    /**
     * @param jedis      redis client
     * @param lockKey    加锁 key
     * @param requestId  哪一个请求加的锁
     * @param expireTime 过期时间
     * @return
     */
    public static boolean tryLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
//        jedis 3.0 中没有此方法
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        //        jedis3.0 写法
//        String result = jedis.set(lockKey, requestId, SetParams.setParams().nx().px(expireTime));
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }


        return false;
    }


    public static boolean unLock(Jedis jedis, String lockKey, String requestId) {
//        lua 脚本
        String script = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";

//        参数下标从 1 开始
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (UNLOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
}
