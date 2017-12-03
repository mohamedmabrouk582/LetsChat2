package com.example.mohamed.letschat.presenter.friends;

import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.MainPresenter;
import com.example.mohamed.letschat.view.FriendsView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 03/12/2017.  time :01:47
 */

public interface FriendsPresenter<v extends FriendsView> extends MainPresenter<v> {
    interface requestListner{
        void onSucess();
    }
    void  userClick(User user, String userkey, boolean me);
    void  unFriend(String uerId,requestListner listner);
}
