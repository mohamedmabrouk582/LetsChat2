package com.example.mohamed.letschat.presenter.requests;

import android.app.Activity;
import android.util.Log;

import com.example.mohamed.letschat.activity.UserInfoActivity;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.RequestView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 02/12/2017.  time :23:48
 */

public class RequestViewPresenter<v extends RequestView> extends BasePresenter<v> implements RequestPresenter<v>{
    private Activity activity;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    public RequestViewPresenter(Activity activity){
        this.activity=activity;
        mReference=MyApp.getDatabaseReference();
        mAuth=MyApp.getmAuth();
    }

    @Override
    public void userClick(User user, String userkey,boolean me) {
        UserInfoActivity.start(activity,user,userkey,me);
    }

    @Override
    public void acceptCancelRequest(final String userid, String type, final requestListner listner) {
        switch (type){
            case "received":
                String date= DateFormat.getDateTimeInstance().format(new Date());
                Map receivedMap=new HashMap<>();
                receivedMap.put("Friends_req/"+mAuth.getCurrentUser().getUid()+"/"+userid+"/request_type",null);
                receivedMap.put("Friends_req/"+userid+"/"+mAuth.getCurrentUser().getUid()+"/request_type",null);

                receivedMap.put("Friends/"+mAuth.getCurrentUser().getUid()+"/"+userid,date);
                receivedMap.put("Friends/"+userid+"/"+mAuth.getCurrentUser().getUid(),date);

                mReference.updateChildren(receivedMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError==null){
                            listner.onSucess();
                        }
                    }
                });
                break;
            case "sent":
                Map sentMap=new HashMap<>();
                sentMap.put("Friends_req/"+mAuth.getCurrentUser().getUid()+"/"+userid+"/request_type",null);
                sentMap.put("Friends_req/"+userid+"/"+mAuth.getCurrentUser().getUid()+"/request_type",null);
                mReference.updateChildren(sentMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError==null){
                            listner.onSucess();
                        }
                    }
                });
                break;
        }
    }



}
