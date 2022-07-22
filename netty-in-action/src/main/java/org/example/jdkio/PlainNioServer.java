package org.example.jdkio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhangming
 * @date 2020/7/7 21:52
 * <p>
 * 非阻塞 I/O
 */
public class PlainNioServer {

    public void serve(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(new InetSocketAddress(port));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi \r\n".getBytes());
        for (; ; ) {
            try {
                selector.select();
            } catch (IOException e) {
                break;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                try {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel client = server.accept();
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                        System.out.println("Accepted connection from " + client);
                    }

                    if (selectionKey.isWritable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        while (buffer.hasRemaining()) {
                            if (client.write(buffer) == 0) {
                                // 缓冲区是空的
                                break;
                            }
                        }
                        client.close();
                    }

                } catch (IOException ex) {
                    selectionKey.cancel();
                    try {
                        selectionKey.channel().close();
                    } catch (IOException cex) {
                        // 在关闭时忽略
                    }
                }
            }
        }
    }
}
