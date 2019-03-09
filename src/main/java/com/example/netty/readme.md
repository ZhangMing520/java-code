BOOTSTRAP
> 用于应用程序网络层配置的容器

CHANNEL
>底层网络传输 API 必须提供给应用 I/O操作的接口

CHANNELHANDLER
> 支持很多协议，并且提供用于数据处理的容器

CHANNELPIPELINE
>提供了一个容器给 ChannelHandler 链并提供了一个API 用于管理沿着链入站和出站事件的流动。每个 Channel 都有自己的ChannelPipeline，当 Channel 创建时自动创建的

EVENTLOOP
>用于处理 Channel 的 I/O 操作。一个单一的 EventLoop通常会处理多个 Channel 事件。一个 EventLoopGroup 可以含有多于一个的 EventLoop 和 提供了一种迭代用于检索清单中的下一个。

CHANNELFUTURE
>所有的 I/O 操作都是异步。提供了接口 ChannelFuture,它的 addListener 方法注册了一个 ChannelFutureListener ，当操作完成时，可以被通知（不管成功与否）。



```
EventLoop 作为一个 Thread 给 Channel 执行工作。
这就是为什么你的应用程序不需要同步 Netty 的 I/O操作;所有 Channel 的 I/O 始终用相同的线程来执行

Bootstrap有1个EventLoopGroup
ServerBootstrap有2个EventLoopGroup
第一个集合包含一个单例 ServerChannel，代表持有一个绑定了本地端口的 socket；第二集合包含所有创建的 Channel，处理服务器所接收到的客户端进来的连接
与 ServerChannel 相关 EventLoopGroup 分配一个 EventLoop 是 负责创建 Channels 用于传入的连接请求。一旦连接接受，第二个EventLoopGroup 分配一个 EventLoop 给它的 Channel。
```

入站和出站（相对用户应用程序）
>入站(ChannelInboundHandler)和出站(ChannelOutboundHandler)之间有一个明显的区别：若数据是从用户应用程序到远程主机则是“出站(outbound)”，相反若数据时从远程主机到用户应用程序则是“入站(inbound)”。

```
在 Netty 发送消息有两种方式。您可以直接写消息给 Channel 或写入 ChannelHandlerContext 对象。主要的区别是， 前一种方法会导致消息从 ChannelPipeline的尾部开始，而 后者导致消息从 ChannelPipeline 下一个处理器开始。
```

Channel main methods（在多线程环境下，所有的方法都是安全的）

|方法名称 | 	描述|
|:-: | 	:-:|
| eventLoop()  |  返回分配给Channel的EventLoop  |
| pipeline() |   返回分配给Channel的ChannelPipeline |
| isActive()  |  返回Channel是否激活，已激活说明与远程连接对等  |
| localAddress()  |  返回已绑定的本地SocketAddress  |
| remoteAddress()	| 返回已绑定的远程SocketAddress  |
| write() | 写数据到远程客户端，数据通过ChannelPipeline传输过去 |
| flush()  | 刷新先前的数据 |
|  writeAndFlush(...)	| 一个方便的方法用户调用write(...)而后调用 flush() |

 Provided transports

| 方法名称 |包 |描述 |
|:-: | 	:-:| :-: |
| NIO | io.netty.channel.socket.nio | 基于java.nio.channels的工具包，使用选择器作为基础的方法。 |
| OIO | io.netty.channel.socket.oio | 基于java.net的工具包，使用阻塞流。|
| Local | io.netty.channel.local | 用来在虚拟机之间本地通信。|
| Embedded | io.netty.channel.embedded | 嵌入传输，它允许在没有真正网络的传输中使用 ChannelHandler，可以非常有用的来测试ChannelHandler的实现。 |
