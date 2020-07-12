import com.zeroc.Ice.Current;

/**
 * @author zhangming
 * @date 2020/7/11 10:30
 */
public class PrinterI implements Demo.Printer {

    @Override
    public void printString(String s, Current current) {
        System.out.println(s);
    }
}
