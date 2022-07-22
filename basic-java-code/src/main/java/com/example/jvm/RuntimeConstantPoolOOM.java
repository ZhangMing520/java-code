package com.example.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangming
 * @date 2019/3/10 21:02
 * <p>
 * -XX:PermSize=10M -XX:MaxPermSize=10M
 * <p>
 * 运行时常量池溢出，intern() 不存在 会将字符串添加到常量池中，并返回此String对象的引用。存在就返回池中字符串对象
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        // 使用list 保持常量池引用，避免 Full gc 回收常量池行为
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i).intern());
        }
    }
}
