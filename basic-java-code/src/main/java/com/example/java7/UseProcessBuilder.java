package com.example.java7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author zhangming
 * @date 2018/12/22 17:02
 * <p>
 * 执行外部命令  例如 bash
 */
public class UseProcessBuilder {

    public static void main(String[] args) throws InterruptedException, IOException {
        ProcessBuilder builder = new ProcessBuilder("grep", "-o", "[A-Za-z_][A-Za-z_0-9]*");

//        输入
        builder.redirectInput(Paths.get("hello.java").toFile());
//        输出
        builder.redirectOutput(Paths.get("identifiers.txt").toFile());

//        将标准输入输出、错误设置为java程序的标准输入输出，错误
//        builder.inheritIO();

        Process process = builder.start();

        process.waitFor();
    }
}
