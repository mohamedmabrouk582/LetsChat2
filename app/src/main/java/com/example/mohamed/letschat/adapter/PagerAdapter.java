package com.example.mohamed.letschat.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
                return new FriendsFragment();
            case 1:
                return new ChatFragment();
            case 2:
                return new RequestsFragment();

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
                return "Friends";
            case 1:
                return "Chat";
            case 2:
                return "Request";

        }

        return null;
    }
}
