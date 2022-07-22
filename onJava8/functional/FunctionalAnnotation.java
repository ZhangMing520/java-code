/**
 * java.util.function 包，包含一组接口，这些接口是 Lambda 表达式和方法引用的目标类型
 * （说白了就是如果Lambda作为实参，那么形参应该怎么定义呢？类型是什么？类型就是 java.util.function 中的函数式接口。当然如果包中没有符合的函数式接口，也可以自定义）
 * <p>
 * 每个接口只包含一个抽象方法，称为函数式方法。@FunctionalInterface 注解是可选的
 * <p>
 * 1. 如果只处理对象而非基本类型，名称则为 Function Consumer Predicate 等。参数类型通过泛型添加
 * <p>
 * 2. 如果接收的参数是基本类型，则由名称的第一部分表示，如 LongConsumer DoubleFunction InePredicate 等，但返回基本类型的 Supplier 接口例外
 * <p>
 * 3. 如果返回值是基本类型，则用 To 表示，如 ToLongFunction<T>  IntToLongFunction。
 * <p>
 * 4. 如果返回值类型与参数类型一致，则是一个运算符：单个参数使用 UnaryOperator 两个参数使用 BinaryOperator
 * <p>
 * 5. 如果接收两个参数且返回值为布尔值，则是一个谓词
 * <p>
 * 6. 如果接收的两个参数类型不同，则名称中有一个 Bi
 * <p>
 * <p>
 * 参考 onJava8 440页
 */

@FunctionalInterface
interface Functional {
    String goodbye(String arg);
}

interface FunctionalNoAnn {
    String goodbye(String arg);
}

/*
@FunctionalInterface
interface NotFunctional{
    String goodbye(String arg);
    String hello(String arg);
}

产生错误信息：
Multiple non-overriding abstract methods found in interface NotFunctional
*/

public class FunctionalAnnotation {

    public String goodbye(String arg) {
        return "Goodbye, " + arg;
    }

    public static void main(String[] args) {
        FunctionalAnnotation fa = new FunctionalAnnotation();
        Functional f = fa::goodbye;
        FunctionalNoAnn fna = fa::goodbye;

        Functional fl = a -> "Goodbye, " + a;
        FunctionalNoAnn fnal = a -> "Goodbye, " + a;
    }
}