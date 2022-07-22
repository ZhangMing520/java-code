/**
 * 捕获构造函数的引用，然后通过引用调用该构造函数
 * <p>
 * 编译器知道调用函数式方法就相当于调用构造函数
 */

class Dog {
    String name;
    int age = -1;

    Dog() {
    }

    Dog(String name) {
        this.name = name;
    }

    Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

interface MakeNoArgs {
    Dog make();
}

interface Make1Args {
    Dog make(String name);
}

interface Make2Args {
    Dog make(String name, int age);
}

public class CtorReference {
    public static void main(String[] args) {
        MakeNoArgs mna = Dog::new;
        Make1Args m1a = Dog::new;
        Make2Args m2a = Dog::new;

        Dog dn = mna.make();
        Dog d1 = m1a.make("Comet");
        Dog d2 = m2a.make("Ralph", 4);
    }
}