import java.util.function.IntSupplier;

/**
 * 当调用返回函数时候 i 和 x 仍然有效，而不是像正常情况下那样在 makeFun() 执行之后 i 和 x 消失了
 */
public class Closure3 {

   /* IntSupplier makeFun(int x) {
        int i = 0;
//        x++  i++ 编译错误
//        被 Lambda 表达式引用的局部变量必须是 final 或者等同 final 效果的
        return () -> x++ + i++;
    }*/
}