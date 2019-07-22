package com.yubraj.gRPC.serverStreaming;

import com.proto.greet.GreetManyRequest;
import com.proto.greet.GreetManyResponse;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public void greetManyTimes(GreetManyRequest request, StreamObserver<GreetManyResponse> responseObserver) {
        String firstName = request.getGreeting().getFirstName();
        String lastName = request.getGreeting().getLastName();

        try {
            for (int i = 0; i < 10; i++) {
                String result = "[" + i + "] Hello : " + firstName + " " + lastName;
                GreetManyResponse response = GreetManyResponse.newBuilder()
                    .setResult(result).build();

                responseObserver.onNext(response);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            responseObserver.onCompleted();
        }
    }
}
