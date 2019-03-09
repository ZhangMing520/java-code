package com.example.java7;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zhangming
 * @date 2018/12/22 16:45
 * <p>
 * 解析路径
 * p.resolve(q)
 * - q 是绝对路径，结果就是q
 * - 否则 q然后q
 * <p>
 * p.resolveSibling(q)
 * q是相对路径，p的兄弟节点
 * <p>
 * p.relativize(q)
 * 获取相对路径  q相对p  ../
 */
public class UsePaths {

    public static void main(String[] args) {
        Path path = Paths.get("/", "home", "tom");
    }
}
