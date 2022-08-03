import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zhangming
 * @date 7/24/22 10:41 PM
 * <p>
 * encrypts a file using caesar cipher
 * <p>
 * 1. javac Caesar
 * 2. java Caesar CaesarTest.class CaesarTest.caesar 3
 * 3. 删除 CaesarTest.class 更改 CaesarTest.java 为 CaesarTest2.java
 * 4. 运行 ClassLoaderTest，输入 CaesarTest
 */
public class Caesar {

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("USAGE: java classLoader.Caesar in out key");
            return;
        }

        try (FileInputStream in = new FileInputStream(args[0]);
             FileOutputStream out = new FileOutputStream(args[1])) {
            int key = Integer.parseInt(args[2]);
            int ch;
            while ((ch = in.read()) != -1) {
//                class 文件加密
                byte c = (byte) (ch + key);
                out.write(c);
            }
        }
    }
}
