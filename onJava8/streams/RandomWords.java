import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangming
 * @date 7/17/22 9:15 PM
 *
 * Stream.generate 可以把任意 Supplier<T> 用于生成 T 类型的流
 */
public class RandomWords implements Supplier<String> {
    Random rand = new Random(47);
    List<String> words = new ArrayList<>();

    public RandomWords(String fname) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fname));
        for (String line : lines.subList(1, lines.size())) {
            for (String word : line.split("[ .?,]+")) {
                words.add(word.toLowerCase());
            }
        }
    }

    @Override
    public String get() {
        return words.get(rand.nextInt(words.size()));
    }

    @Override
    public String toString() {
        return words.stream().collect(Collectors.joining(" "));
    }

    public static void main(String[] args) throws IOException {
        System.out.println(
                Stream.generate(new RandomWords("/home/zhangming/gitspace/java-code/onJava8/streams/Cheese.dat"))
                        .limit(10).collect(Collectors.joining(" "))
        );
    }
}
