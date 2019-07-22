package com.yubraj.gRPC.serverStreaming;

import com.proto.greet.GreetManyRequest;
import com.proto.greet.GreetManyResponse;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class ClientServer {

    public static void main(String[] args) {
        System.out.println("Starting client server");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext().build();
        System.out.println("Creating stubs");

        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        Greeting greeting = Greeting.newBuilder().
                setFirstName("yubraj")
                .setLastName("pokharel")
                .build();

        GreetManyRequest greetManyRequest = GreetManyRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        greetClient.greetManyTimes(greetManyRequest).forEachRemaining(
            x -> System.out.println(x.getResult())
        );

        System.out.println("Shutting dowm channel");
        channel.shutdown();
    }

}
