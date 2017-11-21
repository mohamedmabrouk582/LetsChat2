package com.example.mohamed.letschat.presenter.base;

import com.example.mohamed.letschat.view.MainView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :18:53
 */

public class BasePresenter<v extends MainView> implements MainPresenter<v> {
    private v view;

    public v getView(){
        return view;
    }
    @Override
    public void attachView(v view) {
        this.view=view;
    }
}
