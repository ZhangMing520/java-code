package com.example.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zhangming
 * @date 2019/4/9 14:20
 * <p>
 * 将一个 channel 中的数据迁移到另一个 channel，直接写入文件中了
 */
public class TransferFrom {

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

            toChannel.transferFrom(fromChannel, position, count);

            // 只是显示作用 打印出来，实际上面就结束了
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
