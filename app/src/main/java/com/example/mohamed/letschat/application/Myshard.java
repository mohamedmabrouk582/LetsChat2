package com.example.mohamed.letschat.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mohamed.letschat.data.User;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 21/11/2017.  time :22:35
 */

public class Myshard {
    private final String MyFile="user";
    public static final String NAME="name";
    public static final String EMAIL="email";
    public static final String IMG="img";
    public static final String STATUS="status";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    public Myshard(Context context){
        mSharedPreferences=context.getSharedPreferences(MyFile,Context.MODE_PRIVATE);
        editor=mSharedPreferences.edit();
    }

    public void putUser(String name , String email , String img ,String status){
        editor.putString(NAME,name);
        editor.putString(EMAIL,email);
        editor.putString(IMG,img);
        editor.putString(STATUS,status);
        editor.apply();
    }

    public void putName(String name){
        editor.remove(NAME);
        editor.putString(NAME,name).apply();
    }

    public void putEmail(String email){
        editor.remove(EMAIL);
        editor.putString(EMAIL,email).apply();

    }

    public void putIMG(String img){
        editor.remove(IMG);
        editor.putString(IMG,img).apply();
    }
    public void putStatus(String status){
        editor.remove(STATUS);
        editor.putString(STATUS,status).apply();
    }

    public void clear(){
        mSharedPreferences.edit().clear();
    }

    public User getUser(){
       return new User(mSharedPreferences.getString(NAME,null),mSharedPreferences.getString(EMAIL,null)
               ,mSharedPreferences.getString(IMG,null),mSharedPreferences.getString(STATUS,"I AM USE LET'S CHAT"));
    }
}
