/**
 * 
 * javac Foo.java
 * java Foo
 * 
 * 查看JCOD语法结果
 * java -jar asmtools.jar jdec Foo.class
 * 
 * 由 class 文件生成 jasm 文件
 * java -jar asmtools.jar jasm Foo.class > Foo.jasm.1
 * 
 * iconst_1 将1压入操作数栈
 * iconst_2 将2压入操作数栈
 * istore_1 将操作数栈顶保存至局部变量表1位置
 * iload_1 再将局部变量表1 位置加载到操作数栈顶
 * ifeq L14 判断栈顶位置是否为0 为零则跳转到L14
 * awk 'NR==1,/iconst_1/{sub(/iconst_1/, "iconst_2")} 1' Foo.jasm.1 > Foo.jasm
 * 
 * 由 jasm 文件生成 class 文件
 * java -jar asmtools.jar jasm Foo.jasm
 * 
 * java Foo
 */
public class Foo {
    
    public static void main(String[] args) {
        boolean flag = true ; 
        if (flag) System.out.println("Hello, Java!");
        if (flag == true) System.out.println("Hello, JVM!");
    }
}
