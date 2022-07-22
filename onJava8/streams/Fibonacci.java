import java.util.stream.Stream;

/**
 * @author zhangming
 * @date 7/17/22 9:35 PM
 * <p>
 * Stream.iterate() 产生的流的第一个元素是种子（第一个参数），然后将种子传递给方法（第二个参数）。
 * 方法运行的结果被添加到流（作为流的下一个元素），并被存储起来，作为下次调用 iterate() 方法时候的第一个参数，一次类推
 * <p>
 * 斐波那契数列将最后两个元素进行求和以产生下一个元素
 */
public class Fibonacci {
    int x = 1;

    Stream<Integer> numbers() {
        return Stream.iterate(0, i -> {
            int result = x + i;
            x = i;
            return result;
        });
    }

    public static void main(String[] args) {
        new Fibonacci().numbers().skip(20).limit(10).forEach(System.out::println);
    }
}
