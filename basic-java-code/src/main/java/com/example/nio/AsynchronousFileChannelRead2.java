package com.example.nio;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * @author zhangming
 * @date 2019/4/9 15:29
 * <p>
 * {@link AsynchronousFileChannel}
 * <p>
 * 通过 {@link CompletionHandler}
 */
public class AsynchronousFileChannelRead2 {

    public static void main(String[] args) throws IOException {
        URL resource = BasicChannelExample.class.getClassLoader().getResource("AsynchronousFileChannel.txt");

        AsynchronousFileChannel channel = AsynchronousFileChannel.open(new File(resource.getFile()).toPath(), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;
        channel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                // 一次性可以获取完成
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }
}
