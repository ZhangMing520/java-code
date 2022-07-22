import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author zhangming
 * @date 7/17/22 9:41 PM
 * <p>
 * Stream.Builder  建造者模式（首先创建一个 builder 对象，然后将创建流所需的多个信息传递给它，最后 builder 对象执行创建流的操作）
 */
public class FileToWordsBuilder {

    Stream.Builder<String> builder = Stream.builder();

    public FileToWordsBuilder(String filePath) throws IOException {
        Files.lines(Paths.get(filePath)).skip(1).forEach(line -> {
            for (String word : line.split("[ ,.?]+")) {
                builder.add(word);
            }
        });
    }

    // 只要不调用 build() 就可以继续向 builder 对象中添加单词
    Stream<String> stream() {
        return builder.build();
    }

    public static void main(String[] args) throws IOException {
        new FileToWordsBuilder("/home/zhangming/gitspace/java-code/onJava8/streams/Cheese.dat")
                .stream().limit(7).map(w -> w + " ").forEach(System.out::print);
    }
}
