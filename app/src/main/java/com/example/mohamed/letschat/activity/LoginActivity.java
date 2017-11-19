package com.example.mohamed.letschat.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.mohamed.letschat.fragment.LoginFragment;
import com.example.mohamed.letschat.utils.SingleFragmentActivity;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :23:18
 */

public class LoginActivity extends SingleFragmentActivity {
    public static void Start(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }
    @Override
    public Fragment CreateFragment() {
        return new LoginFragment();
    }
}
