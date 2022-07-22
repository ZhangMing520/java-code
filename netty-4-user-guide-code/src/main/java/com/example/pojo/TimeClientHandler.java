package com.example.pojo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zhangming
 * @date 2020/7/5 17:05
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UnixTime m = (UnixTime) msg;
        System.out.println(m);
        ctx.close();
    }

}
