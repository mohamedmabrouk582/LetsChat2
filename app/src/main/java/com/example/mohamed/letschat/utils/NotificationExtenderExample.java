package com.example.mohamed.letschat.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.notifiy.NotifyPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OSNotificationReceivedResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 06/12/2017.  time :16:36
 */

public class NotificationExtenderExample extends NotificationExtenderService {
    ActivityManager mActivityManager;
    DataManger dataManger;
    boolean res;
    private static final String bkn = "com.example.mohamed.letschat.activity.";


    String type;
    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {
        try {
            MyApp.receivedResult=notification;
            String from=notification.payload.additionalData.getString("from");
            type= notification.payload.additionalData.getString("type");
            FirebaseDatabase.getInstance().getReference().child("Users").child(from).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   MyApp.user=dataSnapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
           // NotifyPresenter.with(getApplicationContext()).onMessageReceived(type,title,body,from);
            Log.d("iii", "jjj" + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataManger=((MyApp) getBaseContext().getApplicationContext()).getDataManger();

        String currentProcess=getActivityManager().getRunningTasks(1).get(0).topActivity.getClassName();
        String currentFragment=dataManger.getFragment();
        if (type.equals("1")){
            if (currentProcess.equals(bkn+"HomeActivity") && currentFragment.equals("Requests")){
                Log.d("where", "i am here" + "");
                res=true;
            }else {
                Log.d("ooo", "ooo" + "");
              res=false;
                //sendNotification(title,body,new Intent(context,HomeActivity.class));
            }

        }else if (type.equals("2")){
            if (currentProcess.equals(bkn+"HomeActivity")){
                res=true;
            }else {
                res=false;
            }
        }
        return res;
    }

    public ActivityManager getActivityManager() {
        if (mActivityManager == null)
            mActivityManager = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        return mActivityManager;
    }
}
