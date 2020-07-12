import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

/**
 * @author zhangming
 * @date 2020/7/11 10:32
 * <p>
 * gradlew :server:build
 */
public class Server {

    public static void main(String[] args) {
        // A communicator starts a number of non-background threads.
        // Destroying the communicator terminates all these threads.

        // initialize the ice run time
        try (Communicator communicator = Util.initialize(args)) {

            // SimplePrinterAdapter  adapter name
            // listen for incoming requests using the default transport protocol (TCP/IP) at port number 10000
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("SimplePrinterAdapter",
                    "default -p 10001");
            PrinterI printerI = new PrinterI();

            // "SimplePrinter" object identity
            adapter.add(printerI, Util.stringToIdentity("SimplePrinter"));
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
