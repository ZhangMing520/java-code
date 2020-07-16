import Ice.Communicator;
import Ice.LocalException;
import Ice.ObjectPrx;
import Ice.Util;
import com.hp.tel.ice.book.Callback_OnlineBook_bookTick;
import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book.OnlineBookPrx;
import com.hp.tel.ice.book.OnlineBookPrxHelper;

/**
 * @author zhangming
 * @date 2020/7/16 21:34
 * <p>
 * 异步调用  经典回调模式
 */
public class MyClientCallback {

    public static void main(String[] args) {
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
            onlineBookPrx.begin_bookTick(message, new Callback_OnlineBook_bookTick() {
                @Override
                public void response(Message __ret) {
                    System.out.println("received response " + __ret.content);
                }

                @Override
                public void exception(LocalException e) {
                    System.out.println(" catch exception " + e);
                }
            });
            System.out.println("invoke");
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
    }

}
