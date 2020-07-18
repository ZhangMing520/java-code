package com.example;

import com.example.helloworld.generate.GreeterGrpc;
import com.example.helloworld.generate.HelloReply;
import com.example.helloworld.generate.HelloRequest;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangming
 * @date 2020/7/16 23:55
 */
public class HelloWorldClient {

    private static GreeterGrpc.GreeterBlockingStub blockingStub;

    /**
     * 使用已经存在的 channel 构建
     *
     * @param channel
     */
    public HelloWorldClient(Channel channel) {
//       is a Channel, not a ManagedChannel, so it is not this code's responsibility to shut it down.
        // reuse Channels
        // 同步调用
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void greet(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        try {
            HelloReply response = blockingStub.sayHello(request);
            System.out.println("Greeting: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed: " + e.getStatus());
            return;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String user = "world";
        String target = "localhost:50051";
        if (args.length > 0) {
            if ("--help".equals(args[0])) {
                System.err.println("Usage: [name [target]]");
                System.err.println("");
                System.err.println("  name    The name you wish to be greeted by. Defaults to " + user);
                System.err.println("  target  The server to connect to. Defaults to " + target);
                System.exit(1);
            }
            user = args[0];
        }
        if (args.length > 1) {
            target = args[1];
        }

        // 创建服务端的通信对象 Channel
        // Channel 是线程安全的和可复用的
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channel 默认是使用 SSL/TLS 的，这里禁用了，避免需要证书麻烦
                .usePlaintext().build();
        try {
            HelloWorldClient client = new HelloWorldClient(channel);
            client.greet(user);
        } finally {
            // ManagedChannel 使用线程和tcp连接等资源，为了防止资源泄漏，我们需要及时关闭
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
