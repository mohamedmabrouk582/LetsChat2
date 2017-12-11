package com.example.mohamed.letschat.presenter.chat;

import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.MainPresenter;
import com.example.mohamed.letschat.view.ChatView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 04/12/2017.  time :20:52
 */

public interface ChatPresenter<v extends ChatView> extends MainPresenter<v> {
    interface  responseLisnter{
        void sucess();
    }
    void send(String msg, String to, User user, responseLisnter lisnter);
}
