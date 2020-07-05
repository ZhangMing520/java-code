package com.example.jvm.diagnostic;

/**
 * @author zhangming
 * @date 2019/3/13 16:27
 * <p>
 * 为了多次载入执行类而加入的加载器
 * <p>
 * 把 defineClass 方法开发出来，只有外部显式调用的时候才会使用到 loadByte 方法
 * 由虚拟机调用时候，仍然按照原有的双亲委派规则使用 loadClass 方法进行类加载
 */
public class HotSwapClassLoader extends ClassLoader {

    HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }
}
