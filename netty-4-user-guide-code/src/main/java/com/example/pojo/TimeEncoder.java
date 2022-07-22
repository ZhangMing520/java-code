package com.example.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author zhangming
 * @date 2020/7/5 17:07
 */
public class TimeEncoder extends ChannelOutboundHandlerAdapter {

    /**
     * 1.通过 ChannelPromise，当编码后的数据被写到通道上，netty可以通过这个对象的标记是成功还是失败
     * 2.不需要调用 ctx.flush() 因为处理器已经单独分离出了一个方法 void flush(ChannelHandlerContext cxt)
     *
     * @param ctx
     * @param msg
     * @param promise
     * @throws Exception
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        UnixTime m = (UnixTime) msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeInt((int) m.value());
        ctx.write(encoded, promise);
    }
}
