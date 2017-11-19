package com.example.mohamed.letschat.presenter;

import android.view.View;

import com.example.mohamed.letschat.view.LoginView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :22:37
 */

public interface LoginPresenter<v extends LoginView> extends MainPresenter<v> {
    void login(String email , String password);
}
