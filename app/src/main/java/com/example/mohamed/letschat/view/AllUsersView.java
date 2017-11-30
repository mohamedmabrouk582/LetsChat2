package com.example.mohamed.letschat.view;

import com.example.mohamed.letschat.data.User;

import java.util.List;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 27/11/2017.  time :23:46
 */

public interface AllUsersView extends MainView {
    void showProgress();
    void hideProgress();
    void showUserClickedMessage(User user);
    void showUsers();
}
