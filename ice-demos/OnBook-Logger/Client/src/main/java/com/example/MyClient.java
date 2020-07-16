package com.example;

import Ice.Communicator;
import Ice.ObjectPrx;
import Ice.Util;
import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book.OnlineBookPrx;
import com.hp.tel.ice.book.OnlineBookPrxHelper;
import com.hp.tel.ice.message.SMSServicePrx;
import com.hp.tel.ice.message.SMSServicePrxHelper;

/**
 * @author zhangming
 * @date 2020/7/12 18:18
 */
public class MyClient {

    public static void main(String[] args) {
        int status = 0;
        Communicator communicator = null;
        try {
            communicator = Util.initialize(args);
            ObjectPrx base = communicator.stringToProxy("OnlineBook:default -p 10000");
            OnlineBookPrx onlineBookPrx = OnlineBookPrxHelper.checkedCast(base);
            if (onlineBookPrx == null) {
                throw new Error("Invalid proxy ");
            }
            Message msg = new Message();
            msg.name = "Mr Wang";
            msg.type = 3;
            msg.price = 99.99;
            msg.valid = true;
            msg.content = "aaaa";
            System.out.println(onlineBookPrx.bookTick(msg).content);


            base = communicator.stringToProxy("SMSService:default -p 10001");
            SMSServicePrx smsServicePrx = SMSServicePrxHelper.checkedCast(base);
            if (smsServicePrx == null) {
                throw new Error("Invalid proxy ");
            }
            smsServicePrx.sendSMS("book: 《zeroc ic》");
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
        System.exit(status);
    }
}
