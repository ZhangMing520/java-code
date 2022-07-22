/**
 * 未绑定的方法引用是指没有关联对象的普通（非静态）方法。在使用未绑定的引用时候，必须先提供对象
 */

class X {
    String f() {
        return "X::f()";
    }
}

interface MakeString {
    String make();
}

interface TransformX {
    String transform(X x);
}

public class UnboundMethodReference {
    public static void main(String[] args) {
        /**
         * make()与f()具有相同的签名，编译也会报错无效方法引用（ invalid method reference ）。这是因为实际上还有一个隐藏的参数：this。
         *
         * 不能在没有 X 对象的前提下调用 f()。因此 X::f 表示未绑定的方法引用，因为它尚未“绑定”到对象
         *
         * 要解决这个问题，我们需要一个 X 对象，所以我们的接口实际上需要一个额外的参数。
         *
         * 使用未绑定的引用时候，函数式方法的签名（接口中的单个方法）不在与方法引用的签名完全匹配。原因是：你需要一个对象来调用方法
         * */
//        MakeString ms = X::f;

        TransformX sp = X::f;
        X x = new X();
        System.out.println(sp.transform(x));
//        同等效果
        System.out.println(x.f());
    }
}