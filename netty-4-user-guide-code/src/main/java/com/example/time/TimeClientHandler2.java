package com.example.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author zhangming
 * @date 2020/7/5 16:16
 * <p>
 * 一个32位整型是非常小的数据，并不见得会被经常拆分到不同的数据段内。然而，它确实可能会被拆分到不同的数据段内，并且
 * 拆分的可能性随着通信量增加而增加
 * <p>
 * 一个接收方不管是客户端还是服务端，都应该把接收到的数据整理成一个或多个更有意思并且能够让程序的业务逻辑更好理解的数据
 * <p>
 * 最简单的方案是构造一个内部的可积累的缓冲，直到4个字节全部接收到了缓冲
 */
public class TimeClientHandler2 extends ChannelInboundHandlerAdapter {

    private ByteBuf buf;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        buf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        buf.release();
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        buf.writeBytes(m);
        m.release();

        if (buf.readableBytes() >= 4) {
            long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
