package org.example;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class MyAgent4 {

    // 通过 instrumentation 注册类加载事件的拦截器
    public static void premain(String args, Instrumentation instrumentation) {
        instrumentation.addTransformer(new MyTransformer());
    }

    static class MyTransformer implements ClassFileTransformer {

        /**
         * 当方法返回之后，Java 虚拟机会使用所返回的 byte 数组，来完成接下来的类加载工作。
         * 不过，如果transform方法返回 null 或者抛出异常，那么 Java 虚拟机将使用原来的 byte 数组完成类加载工作。
         *
         * @param loader
         * @param className
         * @param classBeingRedefined
         * @param protectionDomain
         * @param classfileBuffer     正在被加载的类的字节码
         * @return 代表更新过后的类的字节码
         * @throws IllegalClassFormatException
         */
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//          打印  0xCAFEBABE
            System.out.printf("Loaded %s: 0x%X%X%X%X\n", className, classfileBuffer[0],
                    classfileBuffer[1], classfileBuffer[2], classfileBuffer[3]);
            return null;
        }
    }

}
