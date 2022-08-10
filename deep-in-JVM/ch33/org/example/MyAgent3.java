package org.example;

/***
 * Agent-Class
 * 注意点：
 *      1. HelloWorld中应该等待30s
 *      2. AttachTest 需要attach.dll和tools.jar
 *
 * javac -cp ".;D:\Program Files\java\jdk1.8.0_151\jre\lib\tools.jar" -encoding utf-8 AttachTest.java
 * javac -encoding utf-8 HelloWorld.java
 *
 * java HelloWorld
 * jps
 * java -cp ".;D:\Program Files\java\jdk1.8.0_151\jre\lib\tools.jar" AttachTest 10964 myagent.jar
 */
public class MyAgent3 {

    public static void agentmain(String args) {
        System.out.println("agentmain");
    }

}
