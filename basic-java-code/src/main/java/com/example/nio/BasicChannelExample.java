package com.example.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zhangming
 * @date 2019/4/9 10:33
 */
public class BasicChannelExample {

    public static void main(String[] args) throws IOException {
        URL resource = BasicChannelExample.class.getClassLoader().getResource("log4j2.xml");

        try (RandomAccessFile raf = new RandomAccessFile(new File(resource.getFile()), "rw")) {
            FileChannel channel = raf.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(48);
            int byteRead;
            while ((byteRead = channel.read(buffer)) != -1) {
                System.out.println("Read " + byteRead);
                // make buffer ready for read
                // switch the buffer from writing mode into reading mode using the flip() method call
                buffer.flip();

                while (buffer.hasRemaining()) {
//                    read 1 byte at a time
                    System.out.print((char) buffer.get());
                }

                //  clear the buffer, to make it ready for writing again

                // compact() method only clears the data which you have already read. Any unread data is moved to the beginning of the buffer, and data will now be written into the buffer after the unread data

                // The clear() method clears the whole buffer
                buffer.clear();
            }
        }
    }
}
