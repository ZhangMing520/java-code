package helloNative;

import java.io.PrintWriter;

/**
 * @author zhangming
 * @version 7/21/22 12:00 AM
 * gcc -fPIC -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -shared -o libPrintf4.so Printf4.c
 * javac Printf4Test.java
 * java -Djava.library.path=. Printf4Test
 * <p>
 * 本地方法抛出异常
 */
public class Printf4Test {

    public static void main(String[] args) {
        double price = 44.95;
        double tax = 7.75;
        double amountDue = price * (1 + tax / 100);
        PrintWriter out = new PrintWriter(System.out);

//        this call will throw an exception
        Printf4.fprint(out, "Amount due = %%8.2f", amountDue);
        out.flush();
    }

}
