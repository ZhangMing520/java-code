package com.example.pojo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zhangming
 * @date 2020/7/5 15:59
 * <p>
 * 把一个32位的二进制数据翻译成一个日期或者日历
 */
public class TimeClient {

    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }

        // 如果只指定了一个 EventLoopGroup，它就既会作为一个 bossGroup,也会作为一个 workerGroup
        // 尽管客户端不需要使用到 boss worker
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    // 不是用 childOption() 客户端的 SocketChannel 没有父亲
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();

            // 等待连接关闭
            future.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
