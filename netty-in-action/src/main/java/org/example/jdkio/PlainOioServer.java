package org.example.jdkio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author zhangming
 * @date 2020/7/7 21:44
 * <p>
 * 阻塞I/O
 */
public class PlainOioServer {

    public void server(int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);
        try {
            for (; ; ) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);

                new Thread(new Runnable() {
                    public void run() {
                        OutputStream out;
                        try {
                            out = clientSocket.getOutputStream();
                            out.write("Hi \r\n".getBytes(Charset.forName("UTF-8")));
                            out.flush();
                            clientSocket.close();
                        } catch (IOException e) {
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                // ignore
                            }
                        }
                    }
                }).start();
            }
        } finally {
            serverSocket.close();
        }
    }


}
