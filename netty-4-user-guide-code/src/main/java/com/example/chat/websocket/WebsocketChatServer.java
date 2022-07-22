package com.example.chat.websocket;

import com.sun.security.ntlm.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhangming
 * @date 2020/7/6 0:22
 */
public class WebsocketChatServer {

    private int port;

    public WebsocketChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebsocketChatServerInitializer())
                    // option() 是提供给NioServerSocketChannel 用来接收进来的连接
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // childOption() 是提供给由父管道 ServerChannel 接收到的连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(port).sync();

            future.channel().closeFuture().sync();

            System.out.println("WebsocketChatServer 启动了");
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            System.out.println("WebsocketChatServer 关闭了");
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new WebsocketChatServer(port).run();
    }

}
