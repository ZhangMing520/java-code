1. handler 异常没有被捕获
> 每个 channel 都有一个关联的 ChannelPipeline，它代表了 channelHandler 实例的链。适配器处理的实现只是将一个处理方法调用
>转发到链中的下一个处理器。因此，如果一个 netty 应用程序不覆盖 exceptionCaught ,那么这些错误将最终到达 ChannelPipeline，
>并且结束警告将被记录。出于这个原因，你应该提供至少一个 实现 exceptionCaught 的 ChannelHandler。

2. channelRead0()如何接收服务器数据
> 服务器发送的消息可以以块的形式被接收。即当服务器发送5个字节是不是保证所有的5个字节会立即接收？即使是只有5个字节channelRead0()方法可被调用两次，
>第一次用1个ByteBuf(Netty的字节容器)装载3个字节和第二次用1个ByteBuf装载2个字节。唯一保证的是，该字节将按照它们发送的顺序分别被接收

3. SimpleChannelInboundHandler vs ChannelInboundHandler
> SimpleChannelInboundHandler 会小心的释放对 ByteBuf（保存信息） 的引用，ChannelInboundHandler 不会

4. 出站 入站
> 若数据是从用户应用程序到远程主机则是“出站”，相反数据是从远程主机到用户应用程序则是“入站”

5. 直接写消息给 Channel 或写入 ChannelHandlerContext 对象
> 主要的区别是：前一种方法会导致消息从 ChannelPipeline 的尾部开始，而后者导致消息从 ChannelPipeline  下一个处理器开始