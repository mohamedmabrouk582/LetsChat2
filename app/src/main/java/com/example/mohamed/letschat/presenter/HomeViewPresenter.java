package com.example.mohamed.letschat.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.mohamed.letschat.activity.LoginActivity;
import com.example.mohamed.letschat.activity.SplashActivity;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.view.HomeView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :20:52
 */

public class HomeViewPresenter<v extends HomeView> extends BasePresenter<v> implements HomePresenter<v> {
    private Activity context;
    public HomeViewPresenter(Activity context){
        this.context=context;
    }


    @Override
    public void allFriends() {
        // go to all friends Activity
    }

    @Override
    public void settingsProfile() {
     // go to settings Activity
    }

    @Override
    public void logout() {
        MyApp.getmAuth().signOut();
        SplashActivity.Start(context);
    }
}
