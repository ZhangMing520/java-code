package com.example.nio;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * @author zhangming
 * @date 2019/4/9 15:29
 * <p>
 * {@link AsynchronousFileChannel}
 */
public class AsynchronousFileChannelRead {

    public static void main(String[] args) throws IOException {
        URL resource = BasicChannelExample.class.getClassLoader().getResource("AsynchronousFileChannel.txt");

        AsynchronousFileChannel channel = AsynchronousFileChannel.open(new File(resource.getFile()).toPath(), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;
        Future<Integer> future = channel.read(buffer, position);

        // 无限循环
        while (!future.isDone()) ;

        buffer.flip();
        // 一次性可以获取完成
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
        buffer.clear();
    }
}
