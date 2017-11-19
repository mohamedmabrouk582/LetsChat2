package com.example.mohamed.letschat.view;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :22:35
 */

public interface LoginView extends MainView {
    void login(String email ,String password);
    void createAccount();
    void showProgress();
    void hideProgress();
    void showError(String msg);
}
