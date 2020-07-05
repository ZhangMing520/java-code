package com.example.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhangming
 * @date 2020/7/5 10:40
 * <p>
 * telnet localhost 8080  测试
 */
public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    /**
     * {@link NioEventLoopGroup } 用来处理I/O操作的多线程事件处理器
     * {@link ServerBootstrap} 启动NIO服务的复制启动类
     * {@link ChannelInitializer} 帮助使用着配置一个新的 Channel
     *
     * @throws Exception
     */
    public void run() throws Exception {
        // boos 用来接收进来的连接，一旦boss接收到连接，就会把连接信息注册到 worker上
        // 如何知道多个线程已经被使用，如何映射到已经创建的 Channel 上都需要依赖于 EventLoopGroup 的实现，并且可以通过构造函数来配置他们的关系
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // worker 用来处理已经被接收的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    // 该设置提供给 NioServerSocketChannel 接收到的连接
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 该设置提供给父管道  ServerChannel  接收到的连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口，开始接收请求
            ChannelFuture f = bootstrap.bind(port).sync();

            // 等待服务器 socket 关闭
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).run();
    }
}
