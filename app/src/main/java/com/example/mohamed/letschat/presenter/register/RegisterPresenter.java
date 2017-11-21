package com.example.mohamed.letschat.presenter.register;

import com.example.mohamed.letschat.presenter.base.MainPresenter;
import com.example.mohamed.letschat.view.RegisterView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :23:05
 */

public interface RegisterPresenter<v extends RegisterView> extends MainPresenter<v> {
    void register(String userName, String email, String password);

}
