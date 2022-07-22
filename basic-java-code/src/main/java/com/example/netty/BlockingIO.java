package com.example.netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * @author zhangming
 * @date 2018/12/22 21:35
 * <p>
 * 每个socket对应一个线程
 * <p>
 * 每个线程的默认堆栈分配是128k和1M之间
 */
public class BlockingIO {

    public static void main(String[] args) throws IOException {
        int portNumber = 8080;
        ServerSocket serverSocket = new ServerSocket(portNumber);
        // accept 阻塞直到一個连接被建立
        Socket clientSocket = serverSocket.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String request, response;

        while ((request = in.readLine()) != null) {
            if (Objects.equals("done", request)) {
                break;
            }
        }

        response = processRequest(request);
        out.println(request);
    }

    private static String processRequest(String request) {
        return null;
    }
}
