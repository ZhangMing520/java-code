import Demo.PrinterPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

/**
 * @author zhangming
 * @date 2020/7/11 10:42
 */
public class Client {

    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {
            ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -p 10001");
            PrinterPrx printerPrx = PrinterPrx.checkedCast(base);
            if (printerPrx == null) {
                throw new Error("Invalid proxy");
            }
            printerPrx.printString("Hello world!");
            System.out.println("Send over");
        }
    }

}
