import Ice.Communicator;
import Ice.Identity;
import Ice.ObjectAdapter;
import Ice.Util;

/**
 * @author zhangming
 * @date 2020/7/16 0:03
 */
public class MyServerStarter {

    public static void main(String[] args) {
        Communicator communicator = null;
        try {
            communicator = Util.initialize(args);
            ObjectAdapter onlineBookAdapter = communicator.createObjectAdapterWithEndpoints("OnlineBookAdapter", "default -p 10000");
            Identity identity = communicator.stringToIdentity("OnlineBook");
            onlineBookAdapter.add(new OnlineBookI(), identity);
            onlineBookAdapter.activate();

            System.out.println("server started");
            communicator.waitForShutdown();
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
    }

}
