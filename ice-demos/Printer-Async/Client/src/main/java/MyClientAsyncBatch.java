import Ice.AsyncResult;
import Ice.Communicator;
import Ice.ObjectPrx;
import Ice.Util;
import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book.OnlineBookPrx;
import com.hp.tel.ice.book.OnlineBookPrxHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhangming
 * @date 2020/7/16 0:13
 * <p>
 * 异步调用   批量发送请求并等待结果的场景，通常比同步方法整体性能搞
 */
public class MyClientAsyncBatch {

    private static int batchSize = 10000;

    public static void main(String[] args) throws InterruptedException {
        Communicator communicator = null;
        try {
            communicator = Util.initialize(args);
            ObjectPrx objectPrx = communicator.stringToProxy("OnlineBook:default -p 10000");
            OnlineBookPrx onlineBookPrx = OnlineBookPrxHelper.checkedCast(objectPrx);

            long start = System.currentTimeMillis();
            List<AsyncResult> asyncResultList = new ArrayList<>(batchSize);
            for (int i = 0; i < batchSize; i++) {
                Message message = new Message();
                message.name = "ZeroC Ice权威指南";
                message.type = 1;
                message.valid = true;
                message.price = 99.9;
                message.content = "ice guides";
                AsyncResult asyncResult = onlineBookPrx.begin_bookTick(message);

                asyncResultList.add(asyncResult);
            }

            while (!asyncResultList.isEmpty()) {
                Iterator<AsyncResult> iterator = asyncResultList.iterator();
                while (iterator.hasNext()) {
                    AsyncResult next = iterator.next();
                    if (next.isCompleted()) {
                        Message result = onlineBookPrx.end_bookTick(next);
                        iterator.remove();

                        System.out.println("call finished,return msg " + result.content);
                    }
                }
                Thread.sleep(100);
            }
            System.out.println(System.currentTimeMillis() - start);
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
    }

}
