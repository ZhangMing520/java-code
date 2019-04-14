package com.example.jvm;

import java.io.Serializable;

/**
 * @author zhangming
 * @date 2019/3/13 11:11
 *
 * 从虚拟机内部看重载
 *
 * char  int  long  Character   Serializable  Object  char ...
 *
 * {@link Serializable} 是 {@link Character}类实现的一个接口，会找到装箱类实现了的接口类型
 */
public class Overload {

    public static void sayHello(Object arg){
        System.out.println("hello object");
    }

    public static void sayHello(int arg){
        System.out.println("hello int");
    }

    public static void sayHello(long arg){
        System.out.println("hello long");
    }

    public static void sayHello(Character arg){
        System.out.println("hello Character");
    }

    public static void sayHello(char arg){
        System.out.println("hello char");
    }

    public static void sayHello(char... arg){
        System.out.println("hello char ...");
    }

    public static void sayHello(Serializable arg){
        System.out.println("hello Serializable");
    }


    public static void main(String[] args) {
        sayHello('a');
    }
}
