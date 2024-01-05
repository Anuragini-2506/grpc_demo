package com.example.myapplication;

import static io.grpc.okhttp.internal.Platform.logger;

import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class UserService {

    private static YourServiceGrpc.YourServiceStub asyncStub;
    private static NotificationGrpc.NotificationStub asyncStub2;

    public UserService() {
//        stub = YourServiceGrpc.newBlockingStub(GrpcService.createManagedChannel());
    }

//    public Response connect(ClientInfo.Builder client){
//        ManagedChannel channel = GrpcService.createManagedChannel();
//        stub = YourServiceGrpc.newBlockingStub(channel);
//        ClientInfo request = ClientInfo.newBuilder().setClientId("client-1").build();
//        Response resp = stub.connect(request);
//        return resp;
//    }

    public static void connect() throws Exception {
        ManagedChannel channel = GrpcService.createManagedChannel();
        asyncStub = YourServiceGrpc.newStub(channel);
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<ClientInfo> requestObserver =
                asyncStub.connect(new StreamObserver<Response>() {
                    @Override
                    public void onNext(Response value) {
                       Log.i("GRPC", value.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Status status = Status.fromThrowable(t);
                        logger.log(Level.WARNING, "RouteChat Failed: {0}", status);
                        Log.i("GRPC", "error" + status.toString());
                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                       Log.i("GRPC", "finished connect");
                       finishLatch.countDown();
                    }
                });

        try {
            ClientInfo [] requests = { ClientInfo.newBuilder().setClientId("Client-1").build() };
            for (ClientInfo info : requests) {
                requestObserver.onNext(info);
            }
        }catch(RuntimeException e){
            requestObserver.onError(e);
            throw e;
        }

//        requestObserver.onCompleted();
//        finishLatch.await(1, TimeUnit.MINUTES);
    }


    public static void streamPayload() throws Exception {
        ManagedChannel channel = GrpcService.createNotificationManagedChannel();
        asyncStub2 = NotificationGrpc.newStub(channel); //Notification.newStub(channel);
        final CountDownLatch finishLatch = new CountDownLatch(1);

        NotificationResponseObserver notificationResponseObserver = new NotificationResponseObserver();
        StreamObserver<NotificationAck> notificationRequestObserver = asyncStub2.streamPayload(notificationResponseObserver);
        notificationResponseObserver.startGRPCNotification(notificationRequestObserver);

//        StreamObserver<NotificationAck> requestObserver =
//                asyncStub2.streamPayload(new StreamObserver<NotificationPayload>() {
//                    @Override
//                    public void onNext(NotificationPayload value) {
//                        Log.i("GRPC", value.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        Status status = Status.fromThrowable(t);
//                        logger.log(Level.WARNING, "RouteChat Failed: {0}", status);
//                        Log.i("GRPC", "error" + status.toString());
//                        finishLatch.countDown();
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        Log.i("GRPC", "finished connect");
//                        finishLatch.countDown();
//                    }
//                });
//
//        try {
//            NotificationAck [] requests = { NotificationAck.newBuilder().setNotificationId("123456789").build()};
//            for (NotificationAck info : requests) {
//                requestObserver.onNext(info);
//            }
//        }catch(RuntimeException e){
//            requestObserver.onError(e);
//            throw e;
//        }

//        requestObserver.onCompleted();
//        finishLatch.await(1, TimeUnit.MINUTES);
    }


}
