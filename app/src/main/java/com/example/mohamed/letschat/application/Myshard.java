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
    private static final String TOKEN = "TOKEN";
    private static final String USERID = "USERID";
    private final String MyFile="user";
    public static final String NAME="name";
    public static final String EMAIL="email";
    public static final String IMG="img";
    public static final String STATUS="status";
    public static final String CURRENTFRAGMENT="CURRENTFRAGMENT";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    public Myshard(Context context){
        mSharedPreferences=context.getSharedPreferences(MyFile,Context.MODE_PRIVATE);
        editor=mSharedPreferences.edit();
    }

    public void putUser(String name , String email , String img ,String status,String token){
        editor.putString(NAME,name);
        editor.putString(EMAIL,email);
        editor.putString(IMG,img);
        editor.putString(STATUS,status);
        editor.putString(TOKEN,token);
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

    public void putUserId(String userId){
        editor.remove(USERID);
        editor.putString(USERID,userId);
    }

    public String getUserid(){
        return mSharedPreferences.getString(USERID,null);
    }

    public void putrunningFragment(String frg){
        editor.remove(CURRENTFRAGMENT);
        editor.putString(CURRENTFRAGMENT,frg).apply();

    }
     public String getFragmentName(){
        return mSharedPreferences.getString(CURRENTFRAGMENT,null);
     }

     public void putToken(String token){
         editor.remove(TOKEN);
         editor.putString(TOKEN,token).apply();
     }


    public void putIMG(String img){
        editor.remove(IMG);
        editor.putString(IMG,img).apply();
    }
    public void putStatus(String status){
        editor.remove(STATUS);
        editor.putString(STATUS,status).apply();
    }

    public void removeFragment(){
        editor.remove(CURRENTFRAGMENT).apply();
    }

    public void clear(){
        mSharedPreferences.edit().clear();
    }

    public User getUser(){
       return new User(mSharedPreferences.getString(NAME,null),mSharedPreferences.getString(EMAIL,null)
               ,mSharedPreferences.getString(IMG,null),mSharedPreferences.getString(STATUS,"I AM USE LET'S CHAT"),mSharedPreferences.getString(TOKEN,"done"));
    }
}
