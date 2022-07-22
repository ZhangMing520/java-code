import Ice.Communicator;
import Ice.ObjectAdapter;
import Ice.ObjectPrx;
import Ice.Util;
import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book.OnlineBookPrx;
import com.hp.tel.ice.book.OnlineBookPrxHelper;

/**
 * @author zhangming
 * @date 2020/7/15 23:52
 */
public class MyClient {

    private static int batchSize = 10000;

    public static void main(String[] args) {
        Communicator communicator = null;
        try {
            communicator = Util.initialize(args);
            ObjectPrx objectPrx = communicator.stringToProxy("OnlineBook:default -p 10000");
            if (objectPrx == null) {
                throw new Error("Invalid proxy");
            }
            OnlineBookPrx onlineBookPrx = OnlineBookPrxHelper.checkedCast(objectPrx);

            long start = System.currentTimeMillis();
            for (int i = 0; i < batchSize; i++) {
                Message message = new Message();
                message.name = "ZeroC Ice权威指南";
                message.type = 1;
                message.valid = true;
                message.price = 99.9;
                message.content = "ice guides";
                Message result = onlineBookPrx.bookTick(message);
                System.out.println(result.content);
            }
            System.out.println(System.currentTimeMillis() - start);
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
    }

}
