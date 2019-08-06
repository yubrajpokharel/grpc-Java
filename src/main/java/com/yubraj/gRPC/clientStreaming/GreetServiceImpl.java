package com.yubraj.gRPC.clientStreaming;

import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.LongGreetRequest;
import com.proto.greet.LongGreetResponse;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public StreamObserver<LongGreetRequest> longGreet(StreamObserver<LongGreetResponse> responseObserver) {
        StreamObserver<LongGreetRequest> requestStreamObserver = new StreamObserver<LongGreetRequest>() {

            String result = "";

            @Override
            public void onNext(LongGreetRequest value) {
                result += "Hello [" + value.getGreeting().getFirstName() + " ]";
            }

            @Override
            public void onError(Throwable t) {
                result += "ERROR ";
            }

            @Override
            public void onCompleted() {
                System.out.println("server completed");
                responseObserver.onNext(LongGreetResponse.newBuilder().setResult(result).build());
                responseObserver.onCompleted();
            }
        };

        return requestStreamObserver;
    }
}
