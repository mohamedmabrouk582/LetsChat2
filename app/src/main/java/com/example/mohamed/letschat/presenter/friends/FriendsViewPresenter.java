package com.example.mohamed.letschat.presenter.friends;

import android.app.Activity;

import com.example.mohamed.letschat.activity.UserInfoActivity;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.FriendsView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 03/12/2017.  time :01:49
 */

public class FriendsViewPresenter<v extends FriendsView> extends BasePresenter<v> implements FriendsPresenter<v> {
    private Activity activity;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;

    public FriendsViewPresenter(Activity activity){
        this.activity=activity;
        mDatabaseReference=MyApp.getDatabaseReference().child("Friends");
        mAuth=MyApp.getmAuth();
    }

    @Override
    public void userClick(User user, String userkey, boolean me) {
        UserInfoActivity.start(activity,user,userkey,me);
    }

    @Override
    public void unFriend(final String uerId, final requestListner listner) {
      mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child(uerId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {
           mDatabaseReference.child(uerId).child(mAuth.getCurrentUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                   listner.onSucess();
               }
           });
          }
      });
    }
}
