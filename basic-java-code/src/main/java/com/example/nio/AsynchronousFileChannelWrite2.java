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
 * @date 2019/4/9 15:44
 */
public class AsynchronousFileChannelWrite2 {

    public static void main(String[] args) throws IOException {
        URL resource = BasicChannelExample.class.getClassLoader().getResource("AsynchronousFileChannel.txt");


        AsynchronousFileChannel channel = AsynchronousFileChannel.open(new File(resource.getFile()).toPath(), StandardOpenOption.WRITE);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int position = 0;

        buffer.put("AsynchronousFileChannel write".getBytes());
        buffer.flip();

        channel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                if (null != exc) {
                    exc.printStackTrace();
                }
            }
        });
    }
}
