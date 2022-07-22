package com.example;

import com.example.helloworld.generate.GreeterGrpc;
import com.example.helloworld.generate.HelloReply;

/**
 * @author zhangming
 * @date 2020/7/17 0:20
 */
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(com.example.helloworld.generate.HelloRequest request,
                         io.grpc.stub.StreamObserver<com.example.helloworld.generate.HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
