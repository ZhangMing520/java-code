import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public class CurriedIntAdd {

    public static void main(String[] args) {
//        IntFunction 传入参数是 int，返回类型是 R
//        IntUnaryOperator 传入参数是 int ，返回类型是 int
        IntFunction<IntUnaryOperator> curriedIntAdd = a -> (b -> a + b);

        IntUnaryOperator add4 = curriedIntAdd.apply(4);
        System.out.println(add4.applyAsInt(5));
    }
}