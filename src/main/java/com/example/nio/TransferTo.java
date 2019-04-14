package com.example.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * @author zhangming
 * @date 2019/4/9 14:20
 * <p>
 * 将一个 channel 中的数据迁移到另一个 channel  {@link java.nio.channels.FileChannel#transferFrom(ReadableByteChannel, long, long)} 一样
 */
public class TransferTo {

    public static void main(String[] args) throws IOException {
        URL fromResource = BasicChannelExample.class.getClassLoader().getResource("fromFile.txt");
        URL toResource = BasicChannelExample.class.getClassLoader().getResource("toFile.txt");

        try (RandomAccessFile fromFile = new RandomAccessFile(new File(fromResource.getFile()), "rw");
             RandomAccessFile toFile = new RandomAccessFile(new File(toResource.getFile()), "rw")
        ) {
            FileChannel fromChannel = fromFile.getChannel();
            FileChannel toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();

            fromChannel.transferTo(position, count, toChannel);

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (toChannel.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                buffer.clear();
            }
        }
    }
}
