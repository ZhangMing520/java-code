package com.example;

import Ice.Communicator;
import Ice.Current;
import Ice.ObjectAdapter;
import Ice.ObjectPrx;
import IceBox.Service;
import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book.OnlineBookPrx;
import com.hp.tel.ice.book.OnlineBookPrxHelper;
import com.hp.tel.ice.message._SMSServiceDisp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangming
 * @date 2020/7/12 18:50
 */
public class SMSServiceI extends _SMSServiceDisp implements Service {

    private ObjectAdapter adapter;
    private static final Logger LOGGER = LoggerFactory.getLogger(SMSServiceI.class);

    @Override
    public void start(String name, Communicator communicator, String[] strings) {
        adapter = communicator.createObjectAdapter(name);
        Ice.Object object = this;
        adapter.add(object, communicator.stringToIdentity(name));
        adapter.activate();

        LOGGER.trace("control started! ");
    }

    @Override
    public void stop() {
        LOGGER.trace("control stop! ");
        adapter.destroy();
    }

    @Override
    public void sendSMS(String msg, Current __current) {
        LOGGER.info("sendSMS {} thread id:{} ", msg,Thread.currentThread().getId());
        if (msg.startsWith("book")) {
            // 引入 Registry 后，不管两个服务是否共用一个 Communicator，查找服务的代码都相同
            // 用了间接的 Endpoint 来定位
            ObjectPrx base = adapter.getCommunicator().stringToProxy("OnlineBook@OnlineBookAdapter");
            OnlineBookPrx onlineBookPrx = OnlineBookPrxHelper.checkedCast(base);

            Message bookMsg = new Message();
            bookMsg.name = "Mr Wang";
            bookMsg.type = 3;
            bookMsg.price = 99.99;
            bookMsg.valid = true;
            bookMsg.content = "abcedf";
            onlineBookPrx.bookTick(bookMsg);
        }
    }
}
