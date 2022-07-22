package com.example.jvm.diagnostic;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhangming
 * @date 2019/3/13 17:08
 * <p>
 * JavaClass 执行工具
 */
public class JavaClassExecutor {

    /**
     * 执行外部传过来的代表一个 java 类的 byte 数组
     * <p>
     * 将输入类的 byte 数组中代表 {@link java.lang.System} 的 CONSTANT_Utf8_info 常量修改为劫持后的 {@link HackSystem} 类
     * <p>
     * 执行方法为该类的 static main(String [] args) 方法，输出结果为该类向 System.out/err 输出信息
     *
     * @param classByte 代表一个 java 类的 byte 数组
     * @return 执行结果
     */
    public static String execute(byte[] classByte) {

        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modifyBytes = cm.modifyUTF8Constant("java/lang/System", "com/example/jvm/diagnostic/HackSystem");

        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class klass = loader.loadByte(modifyBytes);

        try {
            Method main = klass.getMethod("main", new Class[]{String[].class});

            main.invoke(null, new String[]{null});
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace(HackSystem.out);
        }

        return HackSystem.getBufferString();
    }

    public static void main(String[] args) throws IOException {

        InputStream stream = JavaClassExecutor.class.getResourceAsStream("JavaClassExecutorTest.class");
        byte[] b =new byte[stream.available()];
        stream.read(b);
        JavaClassExecutor.execute(b);
    }
}
