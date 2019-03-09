package com.example.concurrent.atomic;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author zhangming
 * @date 2018/12/22 11:23
 * <p>
 * 哈希映射会将拥有相同hash的所有数据项保存在同一个块中
 * <p>
 * 通过构造大量相同的hash字符串可以拉低应用程序的速度
 * <p>
 * java8采用树形结构在组织块，之前使用列表的结构
 * <p>
 * 不允许null值
 */
public class UseConcurrentHashMap {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

        // 线程不安全
        Long oldValue = map.get("word");
        Long newValue = oldValue == null ? 1 : oldValue + 1;
        map.put("word", newValue);

        // 第一种
        do {
            oldValue = map.get("word");
            newValue = oldValue == null ? 1 : oldValue + 1;
        } while (!map.replace("word", oldValue, newValue));

        // 第二种
        ConcurrentHashMap<String, LongAdder> map2 = new ConcurrentHashMap<>();
        map2.putIfAbsent("word", new LongAdder()).increment();

        // 第三种  通过计算获取新值
        map.compute("word", (k, v) -> v == null ? 1 : v + 1);

        map2.computeIfAbsent("word", k -> new LongAdder()).increment();


        // 第一次被加入映射时候，需要做一些特殊的事情
//       1L 表示键不存在的初始值，存在就使用后面的函数
//        不像 compute，不会处理键
        map.merge("word", 1L, (existingVal, newVal) -> existingVal + newVal);
        map.merge("word", 1L, Long::sum);


//        三种并行大类型：reduce，search，forEach。 search直到该函数返回一个非 null 值
//        指定并行阈值，如果映射包含的元素数量超过了这个阈值，那么批量操作就以并行方式运行

        // Set 视图，可以删除元素，但是无法添加元素，因为无法添加相应的值 ，具有并发性
//        ConcurrentHashMap 默认是 Boolean
        ConcurrentHashMap.newKeySet();
    }
}
