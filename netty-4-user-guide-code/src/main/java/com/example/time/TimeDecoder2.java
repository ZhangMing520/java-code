package com.example.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author zhangming
 * @date 2020/7/5 16:52
 * <p>
 * {@link ReplayingDecoder} 更简单的解码器
 *
 * <a href="https://netty.io/4.0/xref/io/netty/example/factorial/package-summary.html">对于二进制协议请看 io.netty.example.factorial </a>
 * <a href="https://netty.io/4.0/xref/io/netty/example/telnet/package-summary.html">对于基于文本协议请看 io.netty.example.telnet</a>
 */
public class TimeDecoder2 extends ReplayingDecoder {

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(in.readBytes(4));
    }
}
