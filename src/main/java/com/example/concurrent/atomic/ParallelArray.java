package com.example.concurrent.atomic;

import java.util.Arrays;

/**
 * @author zhangming
 * @date 2018/12/22 14:38
 * <p>
 * 并行数组
 */
public class ParallelArray {

    public static void main(String[] args) {
        String[] words = "ni hao ma".split("\\s+");

        // 对上半部分排序
        Arrays.parallelSort(words, words.length / 2, words.length);

        // i 是元素索引
//        Arrays.parallelSetAll(words, i -> i + "?");

//        前缀累积
        // ni   nihao   nihaoma
        Arrays.parallelPrefix(words, (x, y) -> x + y);
        for (String word : words) {
            System.out.println(word);
        }
    }
}
