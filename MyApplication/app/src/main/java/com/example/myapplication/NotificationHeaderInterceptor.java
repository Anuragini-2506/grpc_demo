package com.example.myapplication;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

import android.util.Log;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

public class NotificationHeaderInterceptor implements ClientInterceptor {
//    static final Metadata.Key<String> CUSTOM_HEADER_KEY =
//            Metadata.Key.of("client-id", Metadata.ASCII_STRING_MARSHALLER);
//    static final Metadata.Key<String> CUSTOM_HEADER_KEY_2 =
//            Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                /* put custom header */
//                headers.put(CUSTOM_HEADER_KEY, "notification:client-1000");
//                headers.put(CUSTOM_HEADER_KEY_2, "token-1000");
//                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
//                    @Override
//                    public void onHeaders(Metadata headers) {
//                        /**
//                         * if you don't need receive header from server,
//                         * you can use {@link io.grpc.stub.MetadataUtils#attachHeaders}
//                         * directly to send header
//                         */
//                        Log.i("GRPC", "header received from server:" + headers);
//                        super.onHeaders(headers);
//                    }
//                }, headers);

                headers.put(Metadata.Key.of("client-id", ASCII_STRING_MARSHALLER), "notification:client-1000");
                headers.put(Metadata.Key.of("token", ASCII_STRING_MARSHALLER), "token-1000");
//                headers.put(Metadata.Key.of("x-client-version", ASCII_STRING_MARSHALLER), "null");
                super.start(responseListener, headers);
            }
        };
    }
}