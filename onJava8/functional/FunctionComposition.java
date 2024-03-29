import java.util.function.Function;

/**
 * f1.compose(f2) 表示 f2 的调用发生在 f1 之前
 */
public class FunctionComposition {

    static Function<String, String> f1 = s -> {
        System.out.println(s);
        return s.replace("A", "_");
    }, f2 = s -> s.substring(3), f3 = s -> s.toLowerCase(), f4 = f1.compose(f2).andThen(f3);

    public static void main(String[] args) {
        System.out.println(f4.apply("GO AFTER ALL AMBULANCES"));
    }
}