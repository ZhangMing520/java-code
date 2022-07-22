package com.example.load_class;

/**
 * @author zhangming
 * @date 2019/3/12 15:22
 * <p>
 * 子类引用父类的静态字段，不会导致子类初始化
 *
 * -XX:+TraceClassLoading
 */
public class NotInitialization {

    /**
     * 对于静态字段，只有直接定义这个字段的类才会被初始化，通过该子类来引用父类中定义的静态字段，只会触发父类的初始化
     */
    public static void main(String[] args) {
        //  SuperClass init
        System.out.println(SubClass.value);
    }
}
