package com.example.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ScatteringByteChannel;

/**
 * @author zhangming
 * @date 2019/4/9 13:36
 */
public class ScatteringRead {

    public static void main(String[] args) throws IOException {
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);

        // 有序 先写满一个 buffer 再写后面的 buffer，适用于固定大小的分块
        ByteBuffer[] bufferArray = {header, body};
        URL resource = BasicChannelExample.class.getClassLoader().getResource("log4j2.xml");

        try (RandomAccessFile raf = new RandomAccessFile(new File(resource.getFile()), "rw")) {
            ScatteringByteChannel channel = raf.getChannel();
            long read;
            while ((read = channel.read(bufferArray)) != -1) {
                header.flip();
                while (header.hasRemaining()) {
                    System.out.print((char) header.get());
                }

                body.flip();
                while (body.hasRemaining()) {
                    System.out.print((char) body.get());
                }

                System.out.println("Header and Body Read " + read);
                header.clear();
                body.clear();
            }
        }
    }
}
