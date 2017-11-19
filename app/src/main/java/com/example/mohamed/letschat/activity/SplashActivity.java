package com.example.mohamed.letschat.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.mohamed.letschat.fragment.SplashFragment;
import com.example.mohamed.letschat.utils.SingleFragmentActivity;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :19:03
 */

public class SplashActivity extends SingleFragmentActivity {

    public static void Start(Context context){
        Intent intent=new Intent(context,SplashActivity.class);
        context.startActivity(intent);
    }
    @Override
    public Fragment CreateFragment() {
        return SplashFragment.newFragment();
    }
}
