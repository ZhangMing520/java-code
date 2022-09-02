package org.example;

/***
 * Premain-Class
 *
 * 需要在 MANIFEST.MF 配置文件中指定 Premain-Class
 *
 * javac -encoding utf-8 org/example/MyAgent.java
 * jar cvmf manifest.txt myagent.jar org/
 *
 * javac -encoding utf-8 HelloWorld.java
 * java -javaagent:myagent.jar HelloWorld
 *
 * */
public class MyAgent2 {

    // 在 main 方法之前执行的方法
    // premain 方法接收的是字符串类型的参数
    public static void premain(String args) {
        System.out.println("premain");
    }

}
