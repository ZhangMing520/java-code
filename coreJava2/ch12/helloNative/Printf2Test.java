package helloNative;

/**
 * @author zhangming
 * @version 2022-07-18 22:14:06
 * <p>
 * gcc -fPIC -I $JAVA_HOME/include -I $JAVA_HOME/include/linux -shared -o libPrintf2.so Printf2.c
 * javac Printf2Test.java
 * java -Djava.library.path=. Printf2Test
 */
class Printf2Test {
    public static void main(String[] args) {
        double price = 44.95;
        double tax = 7.75;
        double amountDue = price * (1 + tax / 100);

        String s = Printf2.sprint("Amount due = %8.2f", amountDue);
        System.out.println(s);
    }
}