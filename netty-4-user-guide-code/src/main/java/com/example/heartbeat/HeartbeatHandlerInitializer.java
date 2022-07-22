package com.example.heartbeat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author zhangming
 * @date 2020/7/6 0:49
 */
public class HeartbeatHandlerInitializer extends ChannelInitializer<Channel> {

    private static final int READ_IDLE_TIME_OUT = 4; // 读超时
    private static final int WRITE_IDLE_TIME_OUT = 5;// 写超时
    private static final int ALL_IDLE_TIME_OUT = 7; // 所有超时

    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(READ_IDLE_TIME_OUT, WRITE_IDLE_TIME_OUT, ALL_IDLE_TIME_OUT));
        pipeline.addLast(new HeartbeatServerHandler());
    }
}
