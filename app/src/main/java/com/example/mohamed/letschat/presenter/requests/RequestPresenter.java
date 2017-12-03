package com.example.mohamed.letschat.presenter.requests;

import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.MainPresenter;
import com.example.mohamed.letschat.view.RequestView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 02/12/2017.  time :23:46
 */

public interface RequestPresenter<v extends RequestView> extends MainPresenter<v> {
    interface requestListner{
        void onSucess();
    }
    void  userClick(User user, String userkey,boolean me);
    void  acceptCancelRequest(String uerId,String type,requestListner listner);
}
