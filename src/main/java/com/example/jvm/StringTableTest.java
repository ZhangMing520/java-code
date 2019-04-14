package com.example.jvm;

import java.util.UUID;

/**
 * @author zhangming
 * @date 2019/3/14 17:32
 * <p>
 * @see java.lang.String#intern
 * <p>
 * -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails  -Xms2G -Xmx2G -Xmn100M
 * <p>
 * xmn: young generation size
 * 新生代设置较小，老年代设置较大，导致的问题：不停的ygc，不做cms gc，并且时间也来越长
 * <p>
 * jvm 中使用 StringTable 的数据结构，本质是 Hashtable
 * 在ygc的过程，需要对 StringTable 进行扫描，保证处于新生代的 String 不会被回收掉
 * <p>
 * 但是在 Full GC或者 CMS GC 会对StringTable做清理
 *
 * 参考博客 <a href="http://lovestblog.cn/blog/2016/11/06/string-intern/"></a>
 */
public class StringTableTest {

    public static void main(String[] args) {
        for (int i = 0; i < 10000000; i++) {
            uuid();
        }
    }

    public static void uuid() {
        UUID.randomUUID().toString().intern();
    }
}
