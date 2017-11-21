package com.example.mohamed.letschat.presenter.splash;

import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.MainPresenter;
import com.example.mohamed.letschat.view.SplashView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :19:27
 */

public interface SplashPresenter<v extends SplashView> extends MainPresenter<v> {
    void openLoginActivity();
    void openHomeActivity(User user);
}
