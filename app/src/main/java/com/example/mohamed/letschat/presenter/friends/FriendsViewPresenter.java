package com.example.mohamed.letschat.presenter.friends;

import android.app.Activity;

import com.example.mohamed.letschat.activity.ChatActivity;
import com.example.mohamed.letschat.activity.UserInfoActivity;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.FriendsView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

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
        mDatabaseReference=MyApp.getDatabaseReference();
        mAuth=MyApp.getmAuth();
    }

    @Override
    public void userClick(User user, String userkey, boolean me) {
      //  UserInfoActivity.start(activity,user,userkey,me);
        activity.startActivity(ChatActivity.newIntent(activity,user,userkey));
    }

    @Override
    public void unFriend(final String userid, final requestListner listner) {
        Map friendsMap=new HashMap();
        friendsMap.put("Friends/"+mAuth.getCurrentUser().getUid()+"/"+userid,null);
        friendsMap.put("Friends/"+userid+"/"+mAuth.getCurrentUser().getUid(),null);

        mDatabaseReference.updateChildren(friendsMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                listner.onSucess();
            }
        });
    }
}
