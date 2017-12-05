package com.example.mohamed.letschat.utils;

import android.util.Log;

import com.example.mohamed.letschat.presenter.notifiy.NotifyPresenter;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 03/12/2017.  time :16:41
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotifyPresenter.with(getBaseContext()).onMessageReceived(remoteMessage);
        Log.d("MyFirebaseMessaging", "onMessageReceived " +  remoteMessage.getData().toString());
    }
}
