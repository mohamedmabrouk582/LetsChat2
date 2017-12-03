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
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 02/12/2017.  time :23:48
 */

public class RequestViewPresenter<v extends RequestView> extends BasePresenter<v> implements RequestPresenter<v>{
    private Activity activity;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    public RequestViewPresenter(Activity activity){
        this.activity=activity;
        mDatabaseReference=MyApp.getDatabaseReference().child("Friends_req");
        mReference=MyApp.getDatabaseReference().child("Friends");
        mAuth=MyApp.getmAuth();
    }

    @Override
    public void userClick(User user, String userkey,boolean me) {
        UserInfoActivity.start(activity,user,userkey,me);
    }

    @Override
    public void acceptCancelRequest(final String uerId, String type, final requestListner listner) {
        switch (type){
            case "received":
                 mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child(uerId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                      mDatabaseReference.child(uerId).child(mAuth.getCurrentUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              beFriends(uerId,listner);
                          }
                      });
                     }
                 });
                break;
            case "sent":
                mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child(uerId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mDatabaseReference.child(uerId).child(mAuth.getCurrentUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                        listner.onSucess();
                            }
                        });
                    }
                });
                break;
        }
    }

    private void beFriends(final String userid, final requestListner listner){
        final String date= DateFormat.getDateTimeInstance().format(new Date());
        mReference.child(mAuth.getCurrentUser().getUid()).child(userid).setValue(date).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
              mReference.child(userid).child(mAuth.getCurrentUser().getUid()).setValue(date).addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                   listner.onSucess();
                  }
              });
            }
        });
    }


}
