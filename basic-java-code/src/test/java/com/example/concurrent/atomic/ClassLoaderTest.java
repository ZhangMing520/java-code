package com.example.concurrent.atomic;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangming
 * @date 2019/3/13 8:49
 * <p>
 * 类加载器与 instanceof 关键字
 * <p>
 *
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".")) + "class";

                    InputStream stream = getClass().getResourceAsStream(fileName);
                    if (stream != null) {
                        byte[] b = new byte[stream.available()];
                        stream.read(b);

                        return defineClass(name, b, 0, b.length);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return super.loadClass(name);
            }
        };


        Object obj = classLoader.loadClass("com.example.concurrent.atomic.ClassLoaderTest").newInstance();

        System.out.println(obj.getClass());
        // true
        System.out.println(obj instanceof com.example.concurrent.atomic.ClassLoaderTest);
    }
}
