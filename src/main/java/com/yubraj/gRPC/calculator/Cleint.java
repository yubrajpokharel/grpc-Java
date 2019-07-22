package com.yubraj.gRPC.calculator;

import static com.proto.calculator.CalculatorServiceGrpc.newBlockingStub;
import static com.proto.calculator.SumRequest.newBuilder;
import static io.grpc.ManagedChannelBuilder.forAddress;

import com.proto.calculator.CalculatorServiceGrpc.CalculatorServiceBlockingStub;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.ManagedChannel;


public class Cleint {
  public static void main(String[] args) {
    ManagedChannel channel = forAddress("localhost", 50051)
        .usePlaintext()
        .build();
    System.out.println("Creating stubs for the calculator stubs");

    CalculatorServiceBlockingStub blockingStub = newBlockingStub(channel);

    SumRequest sumRequest = newBuilder()
        .setFirstNumber(2)
        .setSecondNumber(5)
        .build();

    SumResponse sumResponse = blockingStub.sum(sumRequest);

    System.out.println("the sum from the server is " + sumResponse.getSum());



  }
}
