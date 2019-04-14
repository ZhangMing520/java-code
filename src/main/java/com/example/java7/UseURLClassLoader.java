package com.example.java7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author zhangming
 * @date 2018/12/22 16:58
 * <p>
 * 手动加载jar包
 */
public class UseURLClassLoader {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        URL[] urls = {
                new URL("file:junit-4.11.jar"),
                new URL("file:hamcrest-core-1.3.jar")
        };

        try (URLClassLoader loader = new URLClassLoader(urls)) {
            Class<?> klass = loader.loadClass("org.junit.runner.JunitCore");
        }
    }
}
