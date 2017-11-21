package com.example.mohamed.letschat.presenter.splash;

import android.content.Context;

import com.example.mohamed.letschat.activity.HomeActivity;
import com.example.mohamed.letschat.activity.LoginActivity;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.SplashView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :19:29
 */

public class SplashViewPresenter<v extends SplashView> extends BasePresenter<v> implements SplashPresenter<v>{
    private Context context;

    public SplashViewPresenter(Context context){
        this.context=context;
    }
    @Override
    public void openLoginActivity() {
        LoginActivity.Start(context);
    }

    @Override
    public void openHomeActivity(User user) {
        HomeActivity.Start(context,user);
    }
}
