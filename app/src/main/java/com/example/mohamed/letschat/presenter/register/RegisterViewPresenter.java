package com.example.mohamed.letschat.presenter.register;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.example.mohamed.letschat.activity.HomeActivity;
import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.utils.utils;
import com.example.mohamed.letschat.view.RegisterView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :23:06
 */

public class RegisterViewPresenter<v extends RegisterView> extends BasePresenter<v> implements RegisterPresenter<v> {
    private View view;
    private Activity activity;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private DataManger dataManger;

    public RegisterViewPresenter(View view,Activity  activity){
        this.activity=activity;
        this.view=view;
        mAuth= MyApp.getmAuth();
        mDatabaseReference=MyApp.getDatabaseReference().child("Users");
        mDatabaseReference.keepSynced(true);
        dataManger=((MyApp) activity.getApplication()).getDataManger();
    }
    @Override
    public void register(final String userName, final String email, String password) {
        if(TextUtils.isEmpty(userName)){
            getView().showError("user name  not be empty");
        } else if (TextUtils.isEmpty(password)){
            getView().showError("password not be empty");
                   }else if (password.length()<6){
                       getView().showError("password must be > 6 ");
        } else if (!utils.isEmailValid(email)){
                        getView().showError("your email not valid ");
        } else {
          getView().showProgress();
          mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  getView().hideProgress();

                  if (task.isSuccessful()){
                      addTODataBase(task.getResult().getUser().getUid(),userName,email);
                  }else {
                     getView().showError(task.getException().getMessage());
                  }
              }
          });
        }
    }


    private void addTODataBase(String root,final String name , final String email){
        Map<String,String> map=new HashMap<>();
        map.put("name",name);
        map.put("email",email);
        map.put("imageUrl","default");
        map.put("status","I am Use Let's Chat");
        DatabaseReference reference=mDatabaseReference.child(root);
        reference.setValue(map).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataManger.clear();
                        dataManger.setUser(name,email,"default","I am Use Let's Chat");
                        HomeActivity.Start(activity);
                        activity.finish();
                    }
                });
    }


}
