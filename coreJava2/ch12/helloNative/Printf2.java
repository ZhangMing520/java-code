package helloNative;
/**
 * @author zhangming
 * @version 2022-07-18 22:15:39
 */
class Printf2 {

    public static native String sprint(String format, double x);

    static {
        System.loadLibrary("Printf2");
    }
}