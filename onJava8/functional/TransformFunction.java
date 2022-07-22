import java.util.function.Function;

class I {
    @Override
    public String toString() {
        return "I";
    }
}

class O {
    @Override
    public String toString() {
        return "O";
    }
}

public class TransformFunction {

    //    生成一个与传入的函数具有相同签名的函数
//    先调用in之后调用 andThen()
//    in是传入的函数，andThen是生成的函数
    static Function<I, O> transform(Function<I, O> in) {
//        andThen 之后是生成的函数，函数返回O
        return in.andThen(o -> {
            System.out.println(o);
            return o;
        });
    }

    public static void main(String[] args) {
//       以下 transform 实参就是一个 Lambda 函数，在 andThen 之前被调用
        Function<I, O> f2 = transform(i -> {
            System.out.println(i);
            return new O();
        });

        O o = f2.apply(new I());
    }
}