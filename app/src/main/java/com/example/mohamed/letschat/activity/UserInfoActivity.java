package com.example.mohamed.letschat.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.fragment.UserInfoFragment;
import com.example.mohamed.letschat.utils.SingleFragmentActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/11/2017.  time :17:11
 */

public class UserInfoActivity extends SingleFragmentActivity {

    private static final String USERS = "users";
    private static final String USERKEY="USERKEY";
    private static final String ME = "me";

    public static void start(Context context, User user,String userKey,boolean me){
        Intent intent=new Intent(context,UserInfoActivity.class);
        intent.putExtra(USERS,user);
        intent.putExtra(USERKEY,userKey);
        intent.putExtra(ME,me);
        context.startActivity(intent);
    }

    public static Intent  newIntent(Context context, User user,String userKey,boolean me){
        Intent intent=new Intent(context,UserInfoActivity.class);
        intent.putExtra(USERS,user);
        intent.putExtra(USERKEY,userKey);
        intent.putExtra(ME,me);
        return intent;
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        MyApp.getDatabaseReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("online").setValue(true);
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MyApp.getDatabaseReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("online").setValue(false);
//
//    }
    @Override
    public Fragment CreateFragment() {
        return UserInfoFragment.nerwFragment((User) getIntent().getParcelableExtra(USERS),getIntent().getStringExtra(USERKEY),getIntent().getBooleanExtra(ME,false));
    }
}
