package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.grpc.stub.StreamObserver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserService userService = new UserService();
        try {
//            userService.streamPayload();
            userService.connect();
        } catch(Exception e){
            Log.i("GRPC", e.toString());
            Log.i("GRPC", "kuch toh hua hai");
        }


    }
}