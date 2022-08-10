#### 问题

1. java.util.ServiceConfigurationError: com.sun.tools.attach.spi.AttachProvider: Provider
   sun.tools.attach.WindowsAttachProvider could not be instantiated

> ${JAVA_HOME}/jre/bin/attach.dll 文件没有找到，将这个文件复制到${JAVA_HOME}/bin/目录下即可；
> 前提是${JAVA_HOME}/bin/目录已经加入到操作系统的path环境变量下

```java
// ${JAVA_HOME}\lib\tools.jar
VirtualMachine vm=VirtualMachine.attach(args[0]);
```

2. Failed to find Premain-Class manifest attribute in myagent.jar. Error occurred during initialization of VM. agent
   library failed to init: instrument

> 文件格式问题，可能是java文件或者manifest.txt，manifest.txt概率较大

3. java.io.IOException: invalid header field

> manifest.txt 格式问题，包括一行空行