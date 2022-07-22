import java.util.Random;

public class Randoms {

    public static void main(String[] args) {
//        种子值47（程序再次运行时产生相同的输出）
        new Random(47).ints(5, 20).distinct()
                .limit(7).sorted().forEach(System.out::println);
    }
}