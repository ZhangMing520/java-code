import java.util.function.IntSupplier;

/**
 *
 * 当调用返回函数时候 i 和 x 仍然有效，而不是像正常情况下那样在 makeFun() 执行之后 i 和 x 消失了
 * */
public class Closure2 {

    IntSupplier makeFun(int x) {
        int i = 0;
        return () -> x + i;
    }
}