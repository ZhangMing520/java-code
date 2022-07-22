import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Closure8 {

    /**
     * 每次调用都会创建一个全新而非共享的 ArrayList
     */
    Supplier<List<Integer>> makeFun() {
//        final List<Integer> ai = new ArrayList<>();
        List<Integer> ai = new ArrayList<>();
        ai.add(1);
        return () -> ai;
    }

    public static void main(String[] args) {
        Closure8 c8 = new Closure8();
        List<Integer> l1 = c8.makeFun().get(), l2 = c8.makeFun().get();

        System.out.println(l1);
        System.out.println(l2);

        l1.add(42);
        l2.add(96);

        System.out.println(l1);
        System.out.println(l2);
    }
}