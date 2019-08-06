package com.yubraj.gRPC.clientStreaming;

import com.proto.greet.GreetManyRequest;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import com.proto.greet.LongGreetRequest;
import com.proto.greet.LongGreetResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ClientServer {

    ManagedChannel channel;

    public void init() {
        System.out.println("Starting client server");
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext().build();
        doClientStreamingCall(channel);
        channel.shutdown();
    }

    public static void main(String[] args) {
        ClientServer clientServer = new ClientServer();
        clientServer.init();
    }

    public void doClientStreamingCall(ManagedChannel channel){
        CountDownLatch latch = new CountDownLatch(1);
        GreetServiceGrpc.GreetServiceStub asyncClient = GreetServiceGrpc.newStub(channel);
        StreamObserver<LongGreetRequest> requestStreamObserver = asyncClient.longGreet(new StreamObserver<LongGreetResponse>() {

            @Override
            public void onNext(LongGreetResponse value) {
                System.out.println("Received a response from the  server");
                System.out.println(value.getResult());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.println("Server had completed");
                latch.countDown();
            }
        });

        requestStreamObserver.onNext(LongGreetRequest.newBuilder()
            .setGreeting(Greeting.newBuilder().setFirstName("this"))
        .build());

        requestStreamObserver.onNext(LongGreetRequest.newBuilder()
            .setGreeting(Greeting.newBuilder().setFirstName("is"))
            .build());

        requestStreamObserver.onNext(LongGreetRequest.newBuilder()
            .setGreeting(Greeting.newBuilder().setFirstName("f***ing"))
            .build());

        requestStreamObserver.onNext(LongGreetRequest.newBuilder()
            .setGreeting(Greeting.newBuilder().setFirstName("awesome"))
            .build());

        requestStreamObserver.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
