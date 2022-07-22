import java.io.PrintWriter;

/**
 * @author zhangming
 * @date 7/20/22 11:49 PM
 */
class Printf4 {

    public static native void fprint(PrintWriter ps, String format, double x);

    static {
        System.loadLibrary("Printf4");
    }
}
