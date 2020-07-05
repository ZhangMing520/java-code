1. ByteBuf
- 如果需要，允许使用自定义的缓冲类型
- 复合缓冲类型中内置的透明的零拷贝实现
- 开箱即用的动态缓冲类型，具有像 StringBuffer 一样的动态缓冲能力
- 不在需要调用 flip() 方法
- 正常情况下具有比 ByteBuffer 更快的响应速度

```java
// 透明的零拷贝
// 复合类型与组件类型不兼容。
ByteBuffer[] message = new ByteBuffer[] {header, body};

// 因此，你甚至可以通过混合复合类型与普通缓冲区来创建一个复合类型。
ByteBuf messageWithFooter  = Unpooled.wrappedBuffer(header, body);

// 由于复合类型仍是 ByteBuf，访问其内容很容易，
//并且访问方法的行为就像是访问一个单独的缓冲区，
//即使你想访问的区域是跨多个组件。
//这里的无符号整数读取位于 body 和 footer
messageWithFooter.getUnsignedInt(
     messageWithFooter.readableBytes() - footer.readableBytes() - 1);

```
```java 
// 自动容量扩展
// 一种新的动态缓冲区被创建。在内部，实际缓冲区是被“懒”创建，从而避免潜在的浪费内存空间。
ByteBuf b = Unpooled.buffer(4);

// 当第一个执行写尝试，内部指定初始容量 4 的缓冲区被创建
b.writeByte('1');

b.writeByte('2');
b.writeByte('3');
b.writeByte('4');

// 当写入的字节数超过初始容量 4 时，
//内部缓冲区自动分配具有较大的容量
b.writeByte('5');
```

2. HTTP服务器
- 要求持久化链接以及服务端推送技术的聊天服务（如，[Comet](https://en.wikipedia.org/wiki/Comet_%28programming%29) )
- 需要保持链接甚至整个文件下载完成的媒体流服务（如，2小时长的电影）
- 需要上传大文件并且没有内存压力的文件服务（如，上传1GB文件的请求）
- 支持大规模混合客户端应用用于连接以万计的第三方异步 web 服务

3. websocket 
> websocket通过“Upgrade handshake(升级握手)”从标准的http或者https协议转为websocket。因此，使用 WebSocket 的应用程序将始终以 HTTP/S 开始。在什么时候发生取决于具体应用；
>它可以是在启动时候，或者当一个特定的URL被请求时候

- 客户端/用户连接到服务器并加入聊天
- http 请求页面或者websocket 升级握手
- 服务处理客户端/用户请求
- 响应URI"/"的请求，转到默认 html 页面
- 如果访问的是URI"/ws"，处理websocket升级握手
- 升级握手完成后，通过websocket聊天

> websocket 在“帧”里面来发送数据，其中每一个都代表了一个消息的一部分。一个完整的消息可以利用了多个帧。
>websocket"Request for Comments"(RPC)定义了6中不同的frame；Netty 给他们每个都提供了一个 POJO 实现。
> 只需要显示处理 TextWebSocketFrame，其他的会由 WebSocketServerProtocolHandler 自动处理
- CloseWebSocketFrame
- PingWebSocketFrame
- PongWebSocketFrame
- TextWebSocketFrame



[Netty 4.x User Guide电子书地址](https://waylau.com/netty-4-user-guide/)

[netty官方例子](https://github.com/netty/netty/tree/4.0/example/src/main/java/io/netty/example)