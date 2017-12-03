package com.example.mohamed.letschat.presenter.userInfo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.UserInfoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/11/2017.  time :16:15
 */

public class UserInfoViewPresenter<v extends UserInfoView> extends BasePresenter<v> implements UserInfoPresenter<v> {

    private Activity activity;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    public UserInfoViewPresenter(Activity activity){
        this.activity=activity;
        mDatabaseReference= MyApp.getDatabaseReference().child("Friends_req");
        databaseReference=MyApp.getDatabaseReference().child("Friends");
        mAuth=MyApp.getmAuth();
    }


    @Override
    public void sendFriendRequest(final String userid, String requestType, final requestListner listner) {
      switch (requestType){
          case "not_friends":
                mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child(userid).child("request_type").setValue("sent")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                              if (task.isSuccessful()){
                                  mDatabaseReference.child(userid).child(mAuth.getCurrentUser().getUid()).child("request_type")
                                          .setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void aVoid) {
                                          listner.onSucess("Remove Request");
                                      }
                                  });
                              }else {

                              }
                            }
                        });
              break;
          case "sent":
              mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child(userid).child("request_type").removeValue()
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              mDatabaseReference.child(userid).child(mAuth.getCurrentUser().getUid()).child("request_type").removeValue()
                                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                                          @Override
                                          public void onSuccess(Void aVoid) {
                                              listner.onSucess("Send Friend Request");
                                          }
                                      });
                          }
                      });
              break;
          case "received":
              mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child(userid).child("request_type").removeValue()
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              mDatabaseReference.child(userid).child(mAuth.getCurrentUser().getUid()).child("request_type").removeValue()
                                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                                          @Override
                                          public void onSuccess(Void aVoid) {
                                            beFriends(userid,listner);
                                          }
                                      });
                          }
                      });
              break;
          case "friends":
              databaseReference.child(mAuth.getCurrentUser().getUid()).child(userid).removeValue()
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              databaseReference.child(userid).child(mAuth.getCurrentUser().getUid()).removeValue()
                                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                                          @Override
                                          public void onSuccess(Void aVoid) {
                                              listner.onSucess("Send Friend Request");
                                          }
                                      });
                          }
                      });
              break;

      }
    }

    private void beFriends(final String  userid, final requestListner listner){
        final String date= DateFormat.getDateTimeInstance().format(new Date());
        databaseReference.child(mAuth.getCurrentUser().getUid()).child(userid).setValue(date)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        databaseReference.child(userid).child(mAuth.getCurrentUser().getUid()).setValue(date).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                              listner.onSucess("friends");
                            }
                        });
                    }
                });
    }

    @Override
    public void stateRequest(final String userid, final requestListner listner) {
     mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child(userid).child("request_type").addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             try {
                 listner.onSucess(String.valueOf(dataSnapshot.getValue().toString()));
             }catch (Exception e){
                 databaseReference.child(mAuth.getCurrentUser().getUid()).child(userid).addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         try {

                             listner.onSucess(TextUtils.isEmpty(dataSnapshot.getValue().toString())?"not":"friends");
                         }catch (Exception e){
                             listner.onSucess("not");
                         }
                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });
             }
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     })  ;
    }

    @Override
    public void reject(final String userid, final requestListner listner) {
        mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child(userid).child("request_type").removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mDatabaseReference.child(userid).child(mAuth.getCurrentUser().getUid()).child("request_type").removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        listner.onSucess("Send Friend Request");
                                    }
                                });
                    }
                });
    }


}
