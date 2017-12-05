package com.example.mohamed.letschat.presenter.chat;

import android.app.Activity;

import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.ChatView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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
    public  ChatViewPresenter(Activity activity){
        this.activity=activity;
        mDatabaseReference= MyApp.getDatabaseReference().child("Chats");
        mAuth=MyApp.getmAuth();
    }
    @Override
    public void send(String msg, String to, final responseLisnter lisnter) {
        Date date=new Date();
        Map<String,String> map=new HashMap<>();
        map.put("from",mAuth.getCurrentUser().getUid());
        map.put("to",to);
        map.put("msg",msg);
        map.put("date",String.valueOf(new Timestamp(date.getTime())));
       mDatabaseReference.child(mAuth.getCurrentUser().getUid()+to).push()
       .setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               lisnter.sucess();
           }
       });
    }
}
