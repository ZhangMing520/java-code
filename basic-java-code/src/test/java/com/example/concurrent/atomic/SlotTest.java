package com.example.concurrent.atomic;

/**
 * @author zhangming
 * @date 2019/3/13 9:47
 * <p>
 * slot 复用对垃圾收集的影响
 * <p>
 * -verbose:gc
 */
public class SlotTest {

    public static void main(String[] args) {
        //  placeholder 是否被回收：局部变量表中 slot 是否还存有关于 placeholder 数组对象的引用。
        {
            byte[] placeholder = new byte[64 * 104 * 1024];
        }

        // 无此语句，那么没有任何对局部变量表的读写操作，placeholder原本所用的slot还没有被其他变量所复用，
        // 所以作为 GC Roots 一部分的局部变量表仍然保持着对它的关联。
        int a = 0;

        System.gc();
    }
}
