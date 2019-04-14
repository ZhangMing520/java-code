package com.example.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;

/**
 * @author zhangming
 * @date 2019/4/9 13:45
 */
public class GatheringWrite {

    public static void main(String[] args) throws IOException {
//        ByteBuffer header = ByteBuffer.wrap("header\n".getBytes());
//        ByteBuffer body = ByteBuffer.wrap("body\n".getBytes());

        ByteBuffer header = ByteBuffer.allocate(16);
        ByteBuffer body = ByteBuffer.allocate(16);

        header.clear();
        body.clear();
        header.put("header\n".getBytes());
        body.put("body\n".getBytes());

        header.flip();
        body.flip();

        ByteBuffer[] bufferArray = {header, body};

        URL resource = BasicChannelExample.class.getClassLoader().getResource("GatheringWrite.txt");

        try (RandomAccessFile raf = new RandomAccessFile(new File(resource.getFile()), "rw")) {
            GatheringByteChannel channel = raf.getChannel();

            channel.write(bufferArray);

            channel.close();
        }
    }
}
