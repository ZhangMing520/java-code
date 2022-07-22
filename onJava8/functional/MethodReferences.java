
/**
 * 方法引用
 * <p>
 * 方法引用组成：类名或者对象名，后面跟::，然后跟方法名称
 */
interface Callable {
    void call(String s);
}

class Describe {
    void show(String msg) {
        System.out.println(msg);
    }
}

public class MethodReferences {
    static void hello(String name) {
        System.out.println("Hello, " + name);
    }

    static class Description {
        String about;

        Description(String desc) {
            this.about = desc;
        }

        void help(String msg) {
            System.out.println(about + " " + msg);
        }
    }

    static class Helper {
        static void assist(String msg) {
            System.out.println(msg);
        }
    }

    public static void main(String[] args) {
        Describe d = new Describe();
//        对象名，Java将call()映射到show()
        Callable c = d::show;
        c.call("call()");

//        类名，静态方法引用
        c = MethodReferences::hello;
        c.call("Bob");

        c = new Description("valuable")::help;
        c.call("description");

        c = Helper::assist;
        c.call("Help!");
    }

}