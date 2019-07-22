package com.yubraj.gRPC.calculator;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
  @Override
  public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
    SumRequest sumRequest = request;
    System.out.println("Got request for : " + sumRequest.getFirstNumber() + " and " + sumRequest.getSecondNumber());
    SumResponse sumResponse = SumResponse.newBuilder()
        .setSum(sumRequest.getFirstNumber() + sumRequest.getSecondNumber())
        .build();

    responseObserver.onNext(sumResponse);
    responseObserver.onCompleted();
  }
}
