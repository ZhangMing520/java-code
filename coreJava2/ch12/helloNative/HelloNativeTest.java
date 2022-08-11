package helloNative;

/**
 * @author zhangming
 * @version 2022-07-17 22:42:51
 * <p>
 * 把当前目录添加到库路径中
 * <p>
 * 需要指定 java.library.path, java.library.path=. 表示 .so 文件在当前命令执行目录
 * java -Djava.library.path=. HelloNativeTest
 * <p>
 * 或者设置 LD_LIBRARY_PATH
 * export LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH
 *
 * <a href="https://stackoverflow.com/questions/54114547/unsatisfiedlinkerror-no-libhello-in-java-library-path">unsatisfiedlinkerror-no-libhello-in-java-library-path</a>
 */
class HelloNativeTest {

    static {
        // lib 的名字而不是 .so 文件名
        // 本地方法所在的类名
        System.loadLibrary("HelloNative");
    }

    public static void main(String[] args) {
        HelloNative.greeting();
        System.out.println(HelloNative.getGreeting());
    }
}