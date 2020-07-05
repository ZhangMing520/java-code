package com.example.load_class;

/**
 * @author zhangming
 * @date 2019/3/12 15:45
 * <p>
 * 常量在编译阶段会存入调用类的常量池中，本质没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化
 *
 * {@link ConstClass.HELLO_WORLD} 实际被转化为 NotInitialization3 类对自身常量池的引用了
 * 在编译阶段将常量值 {@link ConstClass.HELLO_WORLD} 存储到了 NotInitialization3 类的常量池
 */
public class NotInitialization3 {

    public static void main(String[] args) {
        System.out.println(ConstClass.HELLO_WORLD);
    }
}
