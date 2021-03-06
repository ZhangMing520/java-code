package com.example.nio;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * @author zhangming
 * @date 2019/4/9 15:44
 */
public class AsynchronousFileChannelWrite {

    public static void main(String[] args) throws IOException {
        URL resource = BasicChannelExample.class.getClassLoader().getResource("AsynchronousFileChannel.txt");


        AsynchronousFileChannel channel = AsynchronousFileChannel.open(new File(resource.getFile()).toPath(), StandardOpenOption.WRITE);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int position = 0;

        buffer.put("AsynchronousFileChannel write".getBytes());

        buffer.flip();
        Future<Integer> future = channel.write(buffer, position);
        buffer.clear();

        while (!future.isDone()) ;

    }
}
