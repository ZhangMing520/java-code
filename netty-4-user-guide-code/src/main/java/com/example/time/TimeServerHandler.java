package com.example.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zhangming
 * @date 2020/7/5 14:59
 * <p>
 * 在不接受任何请求时他会发送一个含32位的整数的消息,并且一旦消息发送就会立即关闭连接
 * <p>
 * 我们会忽略任何接收到的数据，而只是在连接被创建发送一个消息
 *
 * linux命令测试 rdate -o <port> -p <host>
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 在连接被建立并且准备进行通信时候被调用
     * <p>
     * 为了发送一个新的消息，需要分配一个包含这个消息的新的缓冲
     * <p>
     * {@link ByteBuf} 有两个指针，一个对应读操作，一个对应写操作。当向ByteBuf里面写入数据的时候写指针的索引就会增加，同时
     * 读指针的索引没有变化，读指针索引和写指针索引分别代表了消息的开始和消息的结束
     *
     * {@link ChannelFuture} 代表一个还没有发生的I/O操作。任何一个请求操作不会马上被执行，因为netty里的所有操作都是异步的
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture f = ctx.writeAndFlush(time);
//        f.addListener(new ChannelFutureListener() {
//
//            // 已经完成通知我们
//            public void operationComplete(ChannelFuture future) throws Exception {
//                assert f == future;
//                ctx.close();
//            }
//        });

        // 等价于上面代码
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
