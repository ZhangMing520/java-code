package com.example.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author zhangming
 * @date 2020/7/5 11:42
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * ctx.write 不会把数据写到 channel 上，被缓存在内部
     * ctx.flush  把缓存中的数据强行输出
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//
//        ctx.write(msg);
//        ctx.flush();

        ByteBuf in = (ByteBuf) msg;
        System.out.println(in.toString(CharsetUtil.US_ASCII));
        // 等价于上面代码
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
