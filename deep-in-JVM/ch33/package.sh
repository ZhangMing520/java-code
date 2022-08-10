#!/usr/bin/bash

#################### Premain-Class ###################
# 清单文件 manifest2.txt
# java.io.IOException: invalid header field   manifest.txt只有一行数据
javac -encoding utf-8 org/example/MyAgent.java
jar cvmf manifest.txt myagent.jar org/

javac -encoding utf-8 HelloWorld.java
java -javaagent:myagent.jar HelloWorld

#################### Agent-Class ###################

# myagent.jar 标准输出到 HelloWorld 进程
# 10964 来自于运行HelloWorld的Java进程
javac -cp ".;D:\Program Files\java\jdk1.8.0_151\jre\lib\tools.jar" -encoding utf-8 AttachTest.java
javac HelloWorld.java -encoding utf-8
java HelloWorld
jps
java -cp ".;D:\Program Files\java\jdk1.8.0_151\jre\lib\tools.jar" AttachTest 10964 myagent.jar
