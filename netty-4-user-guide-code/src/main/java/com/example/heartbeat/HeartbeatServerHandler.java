package com.example.heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * @author zhangming
 * @date 2020/7/6 0:51
 */
public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {

    // 心跳时候，要发送的内容
    // Return a unreleasable view on the given ByteBuf
    // which will just ignore release and retain calls.
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("Heartbeat", CharsetUtil.UTF_8));


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String type = "";
            if (event.state() == IdleState.READER_IDLE) {
                type = "read idle";
            } else if (event.state() == IdleState.WRITER_IDLE) {
                type = "write idle";
            } else if (event.state() == IdleState.ALL_IDLE) {
                type = "all idle";
            }

            // 心跳内容发送给客户端
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);

            System.out.println(ctx.channel().remoteAddress() + "超时类型：" + type);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
