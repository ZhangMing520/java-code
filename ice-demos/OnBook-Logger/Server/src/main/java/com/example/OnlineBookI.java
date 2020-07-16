package com.example;

import Ice.Communicator;
import Ice.Current;
import Ice.ObjectAdapter;
import IceBox.Service;
import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book._OnlineBookDisp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangming
 * @date 2020/7/12 17:28
 * <p>
 * OnlineBook 服务变成 IceBox 托管服务
 */
public class OnlineBookI extends _OnlineBookDisp implements Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineBookI.class);
    private ObjectAdapter _adapter;

    @Override
    public void start(String name, Communicator communicator, String[] args) {
        // 创建 ObjectAdapter 和 service 同名
        _adapter = communicator.createObjectAdapter(name);
        // 创建 servant 并激活
        Ice.Object object = this;
        _adapter.add(object, communicator.stringToIdentity(name));
        _adapter.activate();
        LOGGER.info("{} started", name);
    }

    @Override
    public void stop() {
        LOGGER.info("{} stoped ", this._adapter.getName());
        // 销毁adapter
        _adapter.destroy();
    }

    @Override
    public Message bookTick(Message msg, Current __current) {
        LOGGER.info("bookTick to call .{} thread id:{}", LOGGER.getClass().getName(),
                Thread.currentThread().getId());

        LOGGER.debug("bookTick called {} ", msg.content);
        return msg;
    }
}
