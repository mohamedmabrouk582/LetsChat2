package com.example.mohamed.letschat.application;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 20/11/2017.  time :00:34
 */

public class MyApp extends Application {
   static   FirebaseAuth mAuth;
    @Override
    public void onCreate() {
        super.onCreate();
        mAuth=FirebaseAuth.getInstance();
    }

    public static FirebaseAuth getmAuth(){
        return mAuth;
    }
}
