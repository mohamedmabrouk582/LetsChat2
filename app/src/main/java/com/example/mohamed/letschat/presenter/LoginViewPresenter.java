package com.example.mohamed.letschat.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.example.mohamed.letschat.activity.HomeActivity;
import com.example.mohamed.letschat.activity.HomesActivity;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.utils.utils;
import com.example.mohamed.letschat.view.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :22:38
 */

public class LoginViewPresenter<v extends LoginView> extends BasePresenter<v> implements LoginPresenter<v> {
    private Activity activity;
    private View view;
    private FirebaseAuth mAuth;
    public LoginViewPresenter(View view, Activity activity){
        this.activity=activity;
        this.view=view;
        mAuth= MyApp.getmAuth();
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
                getView().hideProgress();
                if (task.isSuccessful()){
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
