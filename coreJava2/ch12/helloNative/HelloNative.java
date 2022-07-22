/**
 * @author zhangming
 * @date 2022-07-17 22:02:14
 * 关键词 native 表示本地方法。本地方法既可以是静态的也可以是非静态的
 * <p>
 * C 函数命名规则：
 * <p>
 * 1. 使用完整的 Java 方法名，比如：HelloNative.greeting。
 * 如果该类属于某个包，那么在前面添加包名，比如：com.horstmann.HelloNative.greeting
 * <p>
 * 2. 用下划线替换掉所有的句号，并加上 Java_ 前缀，
 * 例如：Java_HelloNative_greeting 或者 Java_com_horstmann_HelloNative_greeting
 * <p>
 * 3. 如果类名含有非 ASCII 字母或者数字，
 * 如：'_' '$'或者是大于'\u007F'的Unicode字符，用 _0xxxx 来替代它们，xxxx是该字符的Unicode值的4个十六进制数序列
 * <p>
 * 注意：如果你重载了本地方法，必须在名称后附加两个下划线，后面再加上已编码的参数类型。
 * 例如：你有一个本地方法 greeting 和 greeting(int repeat)，那么第一个称为 Java_HelloNative_greeting，第二个称为 Java_HelloNative_greeting__I
 * <p>
 * <p>
 * javac -h . HelloNative.java
 */
class HelloNative {
    public static native void greeting();

    public static native String getGreeting();
}