package com.example.myapplication;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcService {
    public static ManagedChannel createManagedChannel() {
        return ManagedChannelBuilder.forAddress("10.0.2.2", 50051)
//                .executor(Executors.newSingleThreadExecutor())
                .usePlaintext()
                .intercept(new NotificationHeaderInterceptor())
                .keepAliveWithoutCalls(true)
                .build();
    }

    public static ManagedChannel createNotificationManagedChannel() {
        return ManagedChannelBuilder.forAddress("0.tcp.in.ngrok.io", 18200)
//                .executor(Executors.newSingleThreadExecutor())
                .usePlaintext()
                .intercept(new NotificationHeaderInterceptor())
                .keepAliveWithoutCalls(true)
                .keepAliveTime(120, TimeUnit.SECONDS)
                .build();
    }

}
