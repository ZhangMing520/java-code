package org.example;

/***
 * javac -encoding utf8  -h . org/example/Foo.java
 *
 * java org.example.Foo -Djava.library.path=.
 */
public class Foo {

    public static native void foo();

    public native void bar(int i, long j);

    public native void bar(String s, Object o);

    int i = 0xDEADBEEF;

    public static void main(String[] args) {
        try {
            System.loadLibrary("foo");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            System.exit(1);
        }
        new Foo().bar("", "");
    }

}
