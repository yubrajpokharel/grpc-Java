package com.yubraj.gRPC.calculator;

import static io.grpc.ServerBuilder.forPort;

import java.io.IOException;

public class Server{
  public static void main(String[] args) throws IOException, InterruptedException {
    io.grpc.Server server = forPort(50051)
        .addService(new CalculatorServiceImpl())
        .build();

    server.start();

    int noOfProcessor = Runtime.getRuntime().availableProcessors();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Received shutdown Hook");
      server.shutdown();
      System.out.println("successfully shutdown calculator server");
    }));

    server.awaitTermination();
  }
}
