package com.example.mohamed.letschat.presenter.home;

import android.net.Uri;
import android.view.View;

import com.example.mohamed.letschat.presenter.base.MainPresenter;
import com.example.mohamed.letschat.view.HomeView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :20:50
 */

public interface HomePresenter<v extends HomeView> extends MainPresenter<v> {
    void allFriends();

    void edtIMG(Uri uri, CircleImageView view);
    void logout();
}
