/**
 * 消费一个函数，消费函数需要在参数列表正确的描述函数类型
 */

import java.util.function.Function;


class One {
}

class Two {
}

public class ConsumerFunction {
//    消费一个函数，生成具体类型
    static Two consume(Function<One, Two> onetwo) {
        return onetwo.apply(new One());
    }

    public static void main(String[] args) {
        Two two = consume(one -> new Two());
    }
}