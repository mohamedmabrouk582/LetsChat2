package com.example.mohamed.letschat.presenter;

import android.support.v4.app.Fragment;

import com.example.mohamed.letschat.view.HomeView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :20:50
 */

public interface HomePresenter<v extends HomeView> extends MainPresenter<v> {
    void allFriends();
    void settingsProfile();
    void logout();
}
