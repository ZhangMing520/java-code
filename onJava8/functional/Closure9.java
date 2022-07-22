import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Closure9 {

    /**
     * 每次调用都会创建一个全新而非共享的 ArrayList
     */
   /* Supplier<List<Integer>> makeFun() {
        List<Integer> ai = new ArrayList<>();
        // 无法编译
        ai = new ArrayList<>();
        return () -> ai;
    }*/
}