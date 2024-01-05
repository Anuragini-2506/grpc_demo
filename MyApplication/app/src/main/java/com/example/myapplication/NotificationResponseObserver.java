package com.example.myapplication;

import android.util.Log;

import io.grpc.stub.StreamObserver;

public class NotificationResponseObserver implements StreamObserver<NotificationPayload> {

    private StreamObserver<NotificationAck> notificationRequestObserver;

    public void startGRPCNotification( StreamObserver<NotificationAck> notificationRequestObserver){
        this.notificationRequestObserver = notificationRequestObserver;
        Log.i("GRPC", "Starting GRPC Notification");
        this.notificationRequestObserver.onNext(NotificationAck.newBuilder().setNotificationId("hellow").build());
    }

    @Override
    public void onNext(NotificationPayload value) {
        Log.i("GRPC", value.toString());
        this.notificationRequestObserver.onNext(NotificationAck.newBuilder().setNotificationId(value.getId()).build());

    }

    @Override
    public void onError(Throwable t) {
        Log.e("GRPC", t.toString());
    }

    @Override
    public void onCompleted() {
        System.out.println("GRPC Completed");
    }
}
