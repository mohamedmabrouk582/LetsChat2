package com.example.mohamed.letschat.presenter.chat;

import android.app.Activity;
import android.util.Log;

import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.ChatView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 04/12/2017.  time :20:55
 */

public class ChatViewPresenter<v extends ChatView> extends BasePresenter<v> implements ChatPresenter<v> {
    private Activity  activity;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private DataManger dataManger;
    public  ChatViewPresenter(Activity activity){
        this.activity=activity;
        mDatabaseReference= MyApp.getDatabaseReference();
        mAuth=MyApp.getmAuth();
        dataManger=((MyApp) activity.getApplication()).getDataManger();
    }
    @Override
    public void send(final String msg, final String to, final User user, final responseLisnter lisnter) {
        Date date=new Date();
        final Map<String,String> map=new HashMap<>();
        map.put("from",mAuth.getCurrentUser().getUid());
        map.put("to",to);
        map.put("msg",msg);
        map.put("date",String.valueOf(new Timestamp(date.getTime())));
        Map chat=new HashMap();
        String notificationId= FirebaseDatabase.getInstance().getReference().child("notifications").push().getKey();
        Map<String,String> notificationData=new HashMap<>();
        notificationData.put("from",mAuth.getCurrentUser().getUid());
        notificationData.put("msg",msg);
        notificationData.put("type","massage");
        chat.put("Chats/"+mAuth.getCurrentUser().getUid()+"/"+to+"/"+notificationId,map);
        chat.put("Chats/"+to+"/"+mAuth.getCurrentUser().getUid()+"/"+notificationId,map);
        mDatabaseReference.updateChildren(chat, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError==null){


                    lisnter.sucess();
                }
            }
        });

    }
}
