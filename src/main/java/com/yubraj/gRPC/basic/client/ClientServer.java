package com.yubraj.gRPC.basic.client;

import com.proto.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ClientServer {

    public static void main(String[] args) {
        System.out.println("Starting client server");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext().build();
        System.out.println("Creating stubs");

        GreetServiceGrpc.GreetServiceBlockingStub syncGreetClient = GreetServiceGrpc.newBlockingStub(channel);

        Greeting greeting = Greeting.newBuilder().
                setFirstName("yubraj")
                .setLastName("pokharel")
                .build();

        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        GreetResponse greetResponse = syncGreetClient.greet(greetRequest);

        System.out.println(greetResponse.getResult());

        System.out.println("Shutting dowm channel");
        channel.shutdown();
    }

}
