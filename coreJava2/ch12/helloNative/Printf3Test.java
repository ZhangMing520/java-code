package helloNative;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangming
 * @version 2022-07-19 22:12:10
 * <p>
 * gcc -fPIC -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -shared -o libPrintf3.so Printf3.c
 * javac Printf3Test.java
 * java -Djava.library.path=. Printf3Test
 */
class Printf3Test {

    public static void main(String[] args) throws IOException {
        double price = 44.95;
        double tax = 7.75;
        double amountDue = price * (1 + tax / 100);
        PrintWriter out = new PrintWriter(System.out);
        Printf3.fprint(out, "Amount due = %8.2f\n", amountDue);
        out.flush();

        System.out.println(Printf3.getProperty("java.home"));

//        尝试用 C 返回的对象写文件
        FileOutputStream outputStream = Printf3.getFileOutputStream("aaa.txt");
        byte[] bytes = "C invoke <init>".getBytes(StandardCharsets.UTF_8);
        outputStream.write(bytes);
        outputStream.close();

        double[] doubles = Printf3.multiScaleFactor(new double[]{1.0, 2.0, 4.5}, 2.0);
        for (double v : doubles) {
            System.out.println(v);
        }
    }

}