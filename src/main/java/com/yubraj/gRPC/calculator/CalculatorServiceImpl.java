package com.yubraj.gRPC.calculator;

import com.proto.calculator.*;
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

  @Override
  public void primeNumber(PrimeNumberRequest request, StreamObserver<PrimeNumberResponse> responseObserver) {
    Long number = request.getNumber();
    Long divisor = 2L;

    while (number > 1){
      if(number % divisor == 0){
        number /= divisor;
        responseObserver.onNext(PrimeNumberResponse.newBuilder().setNumber(number).build());
      } else {
        divisor += 1;
      }
    }

    responseObserver.onCompleted();
  }
}
