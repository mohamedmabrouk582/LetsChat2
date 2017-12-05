package com.example.mohamed.letschat.utils;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 03/12/2017.  time :16:40
 */

public class FirebaseIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        //   super.onTokenRefresh();

        String id= FirebaseInstanceId.getInstance().getToken();
        Log.d("idno", "id");
    }

    private void sendRegistrationToServer(String token) {
    }
}
