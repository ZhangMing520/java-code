package com.example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @author zhangming
 * @date 2020/7/5 10:29
 * <p>
 * DISCARD(抛弃服务)  这个协议将会抛弃任何收到的数据，而不响应。
 * <p>
 * 处理器的职责是释放所有传递到处理器的引用计数对象
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 事件处理接口，每次从客户端接收到消息的时候会被调用
     * {@link ByteBuf} 引用计数对象，必须显示调用 release() 释放
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 默丢弃掉
        // ((ByteBuf) msg).release();

        ByteBuf in = (ByteBuf) msg;
        try {
            // do something with msg
//           while (in.isReadable()){
//               System.out.println((char) in.readByte());
//               System.out.flush();
//           }

            // 等价于上面代码 更高效
            System.out.println(in.toString(CharsetUtil.US_ASCII));
        } finally {
            // 等价 ((ByteBuf) msg).release()
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 当出现 Throwable 对象才被调用
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
