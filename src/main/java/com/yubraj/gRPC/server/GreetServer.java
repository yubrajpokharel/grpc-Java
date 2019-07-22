package com.yubraj.gRPC.server;

import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello server");
        io.grpc.Server server = ServerBuilder.forPort(50051)
                .addService(new GreetServiceImpl())
                .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            server.shutdown();
            System.out.println("successfully terminated server");
        }));

        server.awaitTermination();
    }
}
