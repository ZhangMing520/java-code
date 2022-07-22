package com.example.chat.websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author zhangming
 * @date 2020/7/5 23:39
 * <p>
 * 处理 http 请求
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;
    private static final File INDEX;

    private final static String FILE_PROTOCOL = "file:";

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String path = location.toURI() + "WebsocketChatClient.html";
            path = !path.contains(FILE_PROTOCOL) ? path : path.substring(FILE_PROTOCOL.length());
            INDEX = new File(path);

        } catch (URISyntaxException e) {
            throw new IllegalStateException("Unable to locate WebsocketChatClient.html", e);
        }
    }

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        if (wsUri.equalsIgnoreCase(req.uri())) {
            // 请求是 websocket 升级，递增引用计数器（保留）并且将它它传递给在 ChannelPipeline 中的下个 ChannelInboundHandler
            // retain() 是必要的，因为 channelRead() 完成后，它会调用 FullHttpRequest 上的 release() 来释放其资源
            ctx.fireChannelRead(req.retain());
        } else {
            if (HttpUtil.is100ContinueExpected(req)) {
                send100Continue(ctx);
            }

            // 读取默认的 WebsocketChatClient.html 页面
            RandomAccessFile file = new RandomAccessFile(INDEX, "r");

            // 响应的第一部分
            HttpResponse response = new DefaultHttpResponse(req.protocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

            // 判断 keepalive 是否在请求头里面
            boolean keepAlive = HttpUtil.isKeepAlive(req);
            if (keepAlive) {
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
//            写 HttpResponse 到客户端
            ctx.write(response);

            // 写 index.html 到客户端
            // 判断 SslHandler 是否在 ChannelPipeline 来决定是使用 DefaultFileRegion 还是 ChunkedNioFile
            if (ctx.pipeline().get(SslHandler.class) == null) {
                    // 如果没有加密也不压缩，要达到最大的效率可以是通过存储 index.html 的内容在一个 DefaultFileRegion 实现。这将利用零拷贝来执行传输
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }

//            写并刷新 LastHttpContent 到客户端，标记响应完成
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

            if (!keepAlive) {
                // 如果 keepalive 没有要求，当写完成时，关闭 Channel
                future.addListener(ChannelFutureListener.CLOSE);
            }
            file.close();
        }
    }

    /**
     * 处理符合 http1.1 "100 continue"请求
     *
     * @param ctx
     */
    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
