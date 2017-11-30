package com.example.mohamed.letschat.presenter.userInfo;

import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.MainPresenter;
import com.example.mohamed.letschat.view.UserInfoView;
import com.google.firebase.auth.UserInfo;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/11/2017.  time :02:51
 */

public interface UserInfoPresenter<v extends UserInfoView> extends MainPresenter<v> {
    //todo User Info
    void sendFriendRequest(String userid);
    void rejectFriendRequest(String userid);
}
