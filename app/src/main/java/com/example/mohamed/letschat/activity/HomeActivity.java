package com.example.mohamed.letschat.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.mohamed.letschat.fragment.HomeFragment;
import com.example.mohamed.letschat.utils.SingleFragmentActivity;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :19:04
 */

public class HomeActivity extends SingleFragmentActivity {

    public static void Start(Context   context){
        Intent intent=new Intent(context,HomeActivity.class);
        context.startActivity(intent);
    }
    @Override
    public Fragment CreateFragment() {
        return HomeFragment.newFragment();
    }
}
