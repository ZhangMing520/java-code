import java.util.function.IntSupplier;

/**
 * 等同 final 效果（Effectively Final）。Java8才出现的术语
 */
public class Closure5 {

   /* IntSupplier makeFun(int x) {
        int i = 0;
        i++;
        x++;
//        Closure2.java 中，如果局部变量的初始值永远不会改变，那么它实际上就是 final 的
//    无法编译通过，如果 x 和 i 的值在方法中的其他位置发生了改变过（但不在返回的函数内部），则编译器将其视为错误
        return () -> x + i;
    }*/
}