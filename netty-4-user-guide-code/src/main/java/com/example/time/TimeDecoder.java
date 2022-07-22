package com.example.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zhangming
 * @date 2020/7/5 16:40
 * <p>
 * {@link TimeDecoder 处理数据拆分问题}
 * <p>
 * ByteToMessageDecoder 会丢弃在累积缓冲里面已经被读取过的数据
 * <p>
 * ByteToMessageDecoder 会持续调用 decode() 直到不放任何数据到out里
 * <p>
 * {@link  TimeDecoder2} 更加简洁版本
 */
public class TimeDecoder extends ByteToMessageDecoder {

    /**
     * 每当有新数据接收的时候，都会被调用，处理内部的累积缓冲
     * <p>
     * decode() 方法增加一个对象到out对象里，意味着解码器解码消息成功
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        out.add(in.readBytes(4));
    }
}
