package com.example.mohamed.letschat.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.example.mohamed.letschat.activity.HomesActivity;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.utils.utils;
import com.example.mohamed.letschat.view.RegisterView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :23:06
 */

public class RegisterViewPresenter<v extends RegisterView> extends BasePresenter<v> implements RegisterPresenter<v> {
    private View view;
    private Activity activity;
    private FirebaseAuth mAuth;

    public RegisterViewPresenter(View view,Activity  activity){
        this.activity=activity;
        this.view=view;
        mAuth= MyApp.getmAuth();
    }
    @Override
    public void register(String userName, String email, String password) {
        if(TextUtils.isEmpty(userName)){
            getView().showError("user name  not be empty");
        } else if (TextUtils.isEmpty(password)){
            getView().showError("password not be empty");
                   }else if (password.length()<6){
                       getView().showError("password must be > 6 ");
        } else if (!utils.isEmailValid(email)){
                        getView().showError("your email not valid ");
        }else {
          getView().showProgress();
          mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  getView().hideProgress();

                  if (task.isSuccessful()){
                      FirebaseUser user = mAuth.getCurrentUser();
                      HomesActivity.Start(activity);
                      activity.finish();

                      }else {
                     getView().showError(task.getException().getMessage());
                  }
              }
          });
        }
    }
}
