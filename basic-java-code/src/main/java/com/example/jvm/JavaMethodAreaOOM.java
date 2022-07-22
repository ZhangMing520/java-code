package com.example.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author zhangming
 * @date 2019/3/10 20:40
 * <p>
 * 方法区内存溢出，此程序使用cglib操作字节码，生成大量的动态类，spring和hibernate都会使用
 * <p>
 * -XX:PermSize=10M -XX:MaxPermSize=10M
 *
 * 错误：PermGen space
 * 大量JSP或者动态生成jsp文件的应用、基于osgi的应用（即使是同一个类文件，被不同的加载器加载也会视为不同的类）
 */
public class JavaMethodAreaOOM {

    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy)
                    -> methodProxy.invokeSuper(o, args));

            enhancer.create();
        }
    }

    static class OOMObject {

    }
}
