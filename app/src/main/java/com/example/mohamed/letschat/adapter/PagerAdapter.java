package com.example.mohamed.letschat.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.fragment.ChatFragment;
import com.example.mohamed.letschat.fragment.FriendsFragment;
import com.example.mohamed.letschat.fragment.RequestsFragment;
import com.example.mohamed.letschat.fragment.SplashFragment;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :21:33
 */

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Log.d("action", "0" + "");
                return new RequestsFragment();
            case 1:
                Log.d("action", "1" + "");

                return new FriendsFragment();
            case 2:
                Log.d("action", "2" + "");

                return new ChatFragment();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Requests";
            case 1:
                return "Friends";
            case 2:
                return "Chat";


        }

        return null;
    }
}
