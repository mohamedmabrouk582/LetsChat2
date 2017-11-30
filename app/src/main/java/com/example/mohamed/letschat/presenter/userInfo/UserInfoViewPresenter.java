package com.example.mohamed.letschat.presenter.userInfo;

import android.app.Activity;

import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.UserInfoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/11/2017.  time :16:15
 */

public class UserInfoViewPresenter<v extends UserInfoView> extends BasePresenter<v> implements UserInfoPresenter<v> {

    private Activity activity;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    public UserInfoViewPresenter(Activity activity){
        this.activity=activity;
    }


    @Override
    public void sendFriendRequest(String userid) {
        
    }

    @Override
    public void rejectFriendRequest(String userid) {

    }
}
