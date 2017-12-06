package com.example.mohamed.letschat.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.fragment.AllUsersFragment;
import com.example.mohamed.letschat.utils.SingleFragmentActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 28/11/2017.  time :00:52
 */

public class AllUsersActivity extends SingleFragmentActivity {

    public static void Start(Context context){
        Intent intent=new Intent(context,AllUsersActivity.class);
        context.startActivity(intent);
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
        return new AllUsersFragment();
    }
}
