package com.example.load_class;

/**
 * @author zhangming
 * @date 2019/3/12 15:41
 * <p>
 * 通过数组定义来引用类，不会触发类的初始化
 */
public class NotInitialization2 {

    public static void main(String[] args) {
        SuperClass[] sca = new SuperClass[10];
    }
}
