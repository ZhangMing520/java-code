/**
 * 高阶函数（Higher-order Function）只是一个消费或者产生函数的函数
 */

import java.util.function.Function;

/**
 * 使用继承，可以轻松地为专用接口创建别名
 */
interface FuncSS extends Function<String, String> {

}

public class ProduceFunction {

    static FuncSS produce() {
//        生成一个函数
//       Lambda 创建和返回一个函数（更推荐使用方法引用）
        return s -> s.toLowerCase();
    }

    public static void main(String[] args) {
        FuncSS f = produce();
        System.out.println(f.apply("YELLING"));
    }

}