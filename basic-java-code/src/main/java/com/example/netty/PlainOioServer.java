package com.example.netty;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author zhangming
 * @date 2018/12/22 23:04
 */
public class PlainOioServer {

    public void server(int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket();

        try {
            for (; ; ) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println("accept connection from " + clientSocket);

                new Thread(() -> {
                    OutputStream out;
                    try {
                        out = clientSocket.getOutputStream();
                        out.write("hi\r\n".getBytes(Charset.forName("UTF-8")));
                        out.flush();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();

                        try {
                            clientSocket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
