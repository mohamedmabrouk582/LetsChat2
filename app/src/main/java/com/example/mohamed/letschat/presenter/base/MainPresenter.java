package com.example.mohamed.letschat.presenter.base;

import android.view.View;

import com.example.mohamed.letschat.view.MainView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :18:52
 */

public interface MainPresenter<v extends MainView> {
    void attachView(v View);
    void showSnakBar(String msg,View view);

}
