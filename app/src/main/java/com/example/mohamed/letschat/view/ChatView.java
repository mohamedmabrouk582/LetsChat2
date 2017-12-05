package com.example.mohamed.letschat.view;

import com.example.mohamed.letschat.data.User;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 04/12/2017.  time :20:39
 */

public interface ChatView extends MainView {
    void showProgress();
    void hideProgress();
    //void showClickedMessage(User user, String userKey, boolean me);
    void showMassages();
    void send();
}
