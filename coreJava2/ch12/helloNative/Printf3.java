import java.io.FileOutputStream;
import java.io.PrintWriter;


class Printf3 {

    //    本地方法C调用Java实例方法
    public static native void fprint(PrintWriter out, String format, double x);

    //    本地方法C调用Java静态方法
    public static native String getProperty(String key);

    //    本地方法C调用构造方法
    public static native FileOutputStream getFileOutputStream(String fileName);

    //    本地方法C操作数组
    public static native double[] multiScaleFactor(double[] doubleArr, double scaleFactor);

    static {
        System.loadLibrary("Printf3");
    }

}