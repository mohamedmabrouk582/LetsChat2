package com.example.mohamed.letschat.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.mohamed.letschat.activity.ChatActivity;
import com.example.mohamed.letschat.activity.UserInfoActivity;
import com.example.mohamed.letschat.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OSNotificationReceivedResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 20/11/2017.  time :00:34
 */

public class MyApp extends Application {
    public static OSNotificationReceivedResult receivedResult;
    static   FirebaseAuth mAuth;
   static DatabaseReference mDatabaseReference;
   static StorageReference mStorageReference;
   DatabaseReference databaseReference;
   DataManger manger;
    String type;
    String from;
    public static User user;


    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .autoPromptLocation(true)
                .setNotificationOpenedHandler(new OneSignal.NotificationOpenedHandler() {
                    @Override
                    public void notificationOpened(OSNotificationOpenResult result) {
                        try {
                             from=receivedResult.payload.additionalData.getString("from");
                             type= receivedResult.payload.additionalData.getString("type");

                                    if (type.equals("1")){
                                        UserInfoActivity.start(getApplicationContext(),user,from,false);
                                    }else if (type.equals("2")){
                                        getApplicationContext().startActivity(ChatActivity.newIntent(getApplicationContext(),user,from));

                                    }


                        }catch (Exception e){

                        }

                    }
                })
                .init();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        Myshard myshard=new Myshard(this);
        manger=new DataManger(myshard);
        if (mAuth.getCurrentUser() !=null) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        databaseReference.child("online").onDisconnect().setValue(false);
                        databaseReference.child("online").setValue(true);
                        try {
                            MyApp.getDatabaseReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lastSeen").setValue(ServerValue.TIMESTAMP);

                        }catch (Exception e){}

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }



    public  DataManger getDataManger(){
        return manger;
    }

    public static StorageReference getStorageReference(){
        return mStorageReference;
    }
    public static DatabaseReference getDatabaseReference(){
        return mDatabaseReference;
    }

    public static FirebaseAuth getmAuth(){
        return mAuth;
    }


    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
}
