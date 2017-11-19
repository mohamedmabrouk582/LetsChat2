package com.example.mohamed.letschat.view;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :23:03
 */

public interface RegisterView extends MainView {
    void register(String userName,String email,String password);
    void login();
    void showProgress();
    void hideProgress();
    void showError(String msg);
}
