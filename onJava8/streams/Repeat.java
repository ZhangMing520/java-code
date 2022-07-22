import java.util.stream.IntStream;

/**
 * @author zhangming
 * @date 7/17/22 9:27 PM
 */
public class Repeat {

    public static void repeat(int n, Runnable action) {
        IntStream.range(0, n).forEach(i -> action.run());
    }

}
