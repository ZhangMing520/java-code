package com.example.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author zhangming
 * @date 2020/7/5 17:12
 */
public class TimeEncoder2 extends MessageToByteEncoder<UnixTime> {

    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) throws Exception {
        out.writeInt((int) msg.value());
    }

}
