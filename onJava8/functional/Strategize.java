/**
 *
 * Lambda 表达式产生函数，而不是类。在JVM上，一切都是一个类，看起来像是函数
 *
 * */
interface Strategy {
    String approach(String msg);
}

class Soft implements Strategy {

    @Override
    public String approach(String msg) {
        return msg.toLowerCase() + "?";
    }
}

class Unrelated {
    static String twice(String msg) {
        return msg + " " + msg;
    }
}

public class Strategize {
    Strategy strategy;
    String msg;

    public Strategize(String msg) {
        strategy = new Soft();
        this.msg = msg;
    }

    void communicate() {
        System.out.println(strategy.approach(msg));
    }

    void changeStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public static void main(String[] args) {
        Strategy[] strategies = {
//                匿名内部类
                new Strategy() {
                    @Override
                    public String approach(String msg) {
                        return msg.toUpperCase() + "!";
                    }
                },
//                lambda表达式
                msg -> msg.substring(0, 5),
//                方法引用
                Unrelated::twice
        };

        Strategize s = new Strategize("Hello there");
        s.communicate();

        for (Strategy strategy : strategies) {
            s.changeStrategy(strategy);
            s.communicate();
        }
    }
}