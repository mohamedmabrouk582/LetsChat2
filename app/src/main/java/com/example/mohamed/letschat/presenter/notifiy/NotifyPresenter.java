package com.example.mohamed.letschat.presenter.notifiy;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.activity.HomeActivity;
import com.example.mohamed.letschat.activity.MyDialog;
import com.example.mohamed.letschat.activity.UserInfoActivity;
import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.utils.ChatModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 04/12/2017.  time :01:08
 */

public class NotifyPresenter {
    private Context context;
    private DataManger dataManger;
    String TAG = "NotifyPresenter";
    public String msg;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;


    static NotifyPresenter notifyPresenter;

    ActivityManager mActivityManager;
    private static final String bkn = "com.example.mohamed.letschat.activity.";
    private int badgeCount;

    NotifyPresenter(Context context) {
        this.context = context;
     dataManger=((MyApp) context.getApplicationContext()).getDataManger();
     mAuth=MyApp.getmAuth();
     mDatabaseReference=MyApp.getDatabaseReference().child("Users");
    }


    public static NotifyPresenter with(Context context) {

        if (notifyPresenter == null) notifyPresenter = new NotifyPresenter(context);

        return notifyPresenter;
    }


   public void onMessageReceived(RemoteMessage remoteMessage){
       String currentProcess=getActivityManager().getRunningTasks(1).get(0).topActivity.getClassName();
       String currentFragment=dataManger.getFragment();
       Log.d("currentProcess", currentProcess+ " : "+currentFragment);
       String type=remoteMessage.getData().get("type");
       String title=remoteMessage.getData().get("title");
       String body=remoteMessage.getData().get("body");
       String from=remoteMessage.getData().get("from_user_id");
       Log.d("payload", remoteMessage.getData()+ "");
       Log.d("type", type + " : "+title +" : "+body+" : "+from);

       if (type.equals("1")){
           if (currentProcess.equals(bkn+"HomeActivity") && currentFragment.equals("Requests")){
             Log.d("where", "i am here" + "");
           }else {
               startRequest(from,title,body);
               //sendNotification(title,body,new Intent(context,HomeActivity.class));
           }
       }else if (type.equals("2")){

       }else if (type.equals("-1")){
           Log.d("logout", "gggg" + "");
        // logOut();
       }
    }



    private void sendNotification(String title, String notify, Intent activity) {
        //if (!SessionManager.isNotificationOn()) return;

        Intent intent = activity;
        // intent.putExtra(SharedKeys.isNotification ,true);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(notify)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(510 /* ID of notification */, notificationBuilder.build());
    }

    private void onNewMsg(String msg, String processName) {
        Log.d(TAG, "onNewMsg " + msg + " processName " + processName);
        ChatModel.getInstance().notifyMsg(msg);

    }

    private void logOut() {
         mAuth.signOut();
        boolean xx = isBackgroundRunning(context);
        Log.d("ForceLogOut", "isBackgroundRunning " + xx);

        if (xx) {
           ForceLogOut.getInstance().notifyLogOut();
        } else {
            Intent intent = new Intent(context, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);

            Intent intent1 = new Intent(context, MyDialog.class);
           context.startActivity(intent1);
        }
    }


    public ActivityManager getActivityManager() {
        if (mActivityManager == null)
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return mActivityManager;
    }

    @SuppressLint("NewApi")
    public boolean isBackgroundRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    Log.d(TAG, "isBackgroundRunning " + activeProcess);

                    if (activeProcess.equals(context.getPackageName())) {
                        //If your app is the process in foreground, then it's not in running in background
                        return false;
                    }
                }
            }
        }

        return true;
    }


    private void startRequest(final String userkey, final String title, final String body){
       mDatabaseReference.child(userkey).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               User user=dataSnapshot.getValue(User.class);
               sendNotification(title,body, UserInfoActivity.newIntent(context,user,userkey,false));

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }


}
