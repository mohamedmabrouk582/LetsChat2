package com.example.mohamed.letschat.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamed.letschat.R;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :18:56
 */

public abstract class SingleFragmentActivity extends AppCompatActivity{
   public abstract Fragment CreateFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.Fragment_Container);

        if (fragment==null) {
          fragment=CreateFragment();
          fragmentManager.beginTransaction().add(R.id.Fragment_Container,fragment).commit();
        }
    }
}
