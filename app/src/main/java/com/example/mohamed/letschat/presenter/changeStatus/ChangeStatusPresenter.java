package com.example.mohamed.letschat.presenter.changeStatus;

import com.example.mohamed.letschat.presenter.base.MainPresenter;
import com.example.mohamed.letschat.view.ChangeStatusView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 27/11/2017.  time :22:34
 */

public interface ChangeStatusPresenter<v extends ChangeStatusView>  extends MainPresenter<v>{
    void save(String status);
}
