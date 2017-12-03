package com.example.mohamed.letschat.view;

import com.example.mohamed.letschat.data.User;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 02/12/2017.  time :23:45
 */

public interface RequestView extends MainView {
    void showProgress();
    void hideProgress();
    void showUserClickedMessage(User user, String userKey,boolean me);
    void showUsers();
}
