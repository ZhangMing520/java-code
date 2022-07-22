import Ice.*;
import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book.OnlineBookPrx;
import com.hp.tel.ice.book.OnlineBookPrxHelper;

/**
 * @author zhangming
 * @date 2020/7/16 0:13
 * <p>
 * 异步调用
 */
public class MyClientAsync {

    public static void main(String[] args) throws InterruptedException {
        Communicator communicator = null;
        try {
            communicator = Util.initialize(args);
            ObjectPrx objectPrx = communicator.stringToProxy("OnlineBook:default -p 10000");
            OnlineBookPrx onlineBookPrx = OnlineBookPrxHelper.checkedCast(objectPrx);

            Message message = new Message();
            message.name = "ZeroC Ice权威指南";
            message.type = 1;
            message.valid = true;
            message.price = 99.9;
            message.content = "ice guides";
            AsyncResult asyncResult = onlineBookPrx.begin_bookTick(message);
            while (true) {
                if (asyncResult.isCompleted()) {
                    Message result = onlineBookPrx.end_bookTick(asyncResult);
                    System.out.println("call finished,return msg " + result.content);
                    break;
                } else {
                    System.out.println("wait for finished ");
                    Thread.sleep(100);
                }
            }
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
    }

}
