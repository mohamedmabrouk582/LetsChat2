package com.example.mohamed.letschat.presenter.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.mohamed.letschat.activity.HomeActivity;
import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.presenter.login.LoginPresenter;
import com.example.mohamed.letschat.utils.utils;
import com.example.mohamed.letschat.view.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :22:38
 */

public class LoginViewPresenter<v extends LoginView> extends BasePresenter<v> implements LoginPresenter<v> {
    private Activity activity;
    private View view;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private DataManger dataManger;
    public LoginViewPresenter(View view, Activity activity){
        this.activity=activity;
        this.view=view;
        mAuth= MyApp.getmAuth();
        mDatabaseReference=MyApp.getDatabaseReference();
        dataManger=((MyApp) activity.getApplication()).getDataManger();
    }


    @Override
    public void login(String email, String password) {
       if (TextUtils.isEmpty(password)){
           getView().showError("password not be empty");
       }else if (password.length()<6){
           getView().showError("password must be > 6 ");
       } else if (!utils.isEmailValid(email)){
         getView().showError("your email not valid ");
       }else {
        getView().showProgress();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    start(task.getResult().getUser().getUid());
                }else {
                 getView().showError(task.getException().getMessage());
                }

                         }
        });
       }
    }

    private void start(final String root){
        mDatabaseReference.child("Users").child(root).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final String deviceToken= FirebaseInstanceId.getInstance().getToken();
                Log.d("deviceToken", deviceToken+ "");
                User user=dataSnapshot.getValue(User.class);

//                if (!user.getDevice_token().equals(deviceToken) && !user.getDevice_token().equals("null")){
//                   sendlogoutrequestFromDevice(dataManger.getUserId(),user,deviceToken,root);
//
//                }else {
                OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
                    @Override
                    public void idsAvailable(String userId, String registrationId) {
                        mDatabaseReference.child("Users").child(root).child("device_token").setValue(userId).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                getView().hideProgress();
                                User user=dataSnapshot.getValue(User.class);
                                dataManger.clear();
                                dataManger.setUserId(mAuth.getCurrentUser().getUid());
                                dataManger.setUser(user.getName(),user.getEmail(),user.getImageUrl(),user.getStatus(),user.getDevice_token());
                                HomeActivity.Start(activity,true);
                                activity.finish();


                            }
                        });
                    }
                });


                }

          //  }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //TODO LOGOUT FROM  ANOTHER DEVICES

    private void sendlogoutrequestFromDevice(String userKey, final User user, final String deviceToken, final String root){
        Map<String,String> notificationData=new HashMap<>();
        notificationData.put("from",mAuth.getCurrentUser().getUid());
        notificationData.put("type","login");
        mDatabaseReference.child("notifications").child(userKey).push().setValue(notificationData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseReference.child("Users").child(root).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getView().hideProgress();
                        dataManger.clear();
                        dataManger.setUserId(mAuth.getCurrentUser().getUid());
                        dataManger.setUser(user.getName(),user.getEmail(),user.getImageUrl(),user.getStatus(),user.getDevice_token());
                        HomeActivity.Start(activity,true);
                        activity.finish();

                    }
                });
                return;
            }
        });
    }
}
