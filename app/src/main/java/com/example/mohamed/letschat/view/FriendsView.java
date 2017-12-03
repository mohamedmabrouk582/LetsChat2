package com.example.mohamed.letschat.view;

import com.example.mohamed.letschat.data.User;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 03/12/2017.  time :01:46
 */

public interface FriendsView extends MainView {
    void showProgress();
    void hideProgress();
    void showUserClickedMessage(User user, String userKey, boolean me);
    void showUsers();
}
