package com.example.mohamed.letschat.presenter.notifiy;

import android.util.Log;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 05/12/2017.  time :00:06
 */

public class ForceLogOut {
    private static final ForceLogOut ourInstance = new ForceLogOut();

    private logOutListener listener;

    public interface logOutListener {
        void onForceLogOut();
    }

    public  static ForceLogOut getInstance() {
        return ourInstance;
    }

    private ForceLogOut() {

    }

    public void setListener(logOutListener listener) {
        this.listener = listener;
        Log.d("ForceLogOut", "setListener" + "");
    }

    public void notifyLogOut() {
        Log.d("ForceLogOut", "notifyLogOut" + "");

        if (listener != null) listener.onForceLogOut();
    }
}
