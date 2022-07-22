/**
 * 递归的Lambda表达式，递归方法必须是实例变量或者静态变量，否则会出现编译时错误
 * <p>
 * 从本质上看，lambda还是一个类
 */
public class RecursiveFibonacci {
    //    fib 是实例变量
    IntCall fib;

    RecursiveFibonacci() {
        fib = n -> n == 0 ? 0 : (n == 1 ? 1 : fib.call(n - 1) + fib.call(n - 2));
    }

    int fibonacci(int n) {
        return fib.call(n);
    }


    public static void main(String[] args) {
        RecursiveFibonacci rf = new RecursiveFibonacci();
        for (int i = 0; i <= 10; i++) {
            System.out.println(rf.fibonacci(i));
        }
    }
}