import java.util.function.Function;

/**
 * 柯里化（Curring）：将一个多参数的函数，转化为一系列单参数函数。
 */

public class CurryingAndPartials {

    // 未被柯里化
    static String uncurried(String a, String b) {
        return a + b;
    }

    public static void main(String[] args) {
//        第二个接口是一个函数
//        可以理解为固定了第一个参数a，返回了一个函数（此函数的参数是b）
        Function<String, Function<String, String>> sum = a -> b -> a + b;

        System.out.println(uncurried("Hi ", "Ho"));

        Function<String, String> hi = sum.apply("Hi ");
        System.out.println(hi.apply("Ho"));

        Function<String, String> sumHi = sum.apply("Hup ");
        System.out.println(sumHi.apply("Ho"));
        System.out.println(sumHi.apply("Hey"));
    }

}