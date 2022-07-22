/**
 * 递归的Lambda表达式，递归方法必须是实例变量或者静态变量，否则会出现编译时错误
 * <p>
 * 从本质上看，lambda还是一个类
 */
interface IntCall {
    int call(int arg);
}

public class RecursiveFactorial {
    //        fact 是静态变量
    static IntCall fact;

    public static void main(String[] args) {

        fact = n -> n == 0 ? 1 : n * fact.call(n - 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(fact.call(i));
        }
    }
}