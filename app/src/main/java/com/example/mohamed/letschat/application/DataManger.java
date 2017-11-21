package com.example.mohamed.letschat.application;

import android.content.SharedPreferences;

import com.example.mohamed.letschat.data.User;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 21/11/2017.  time :22:46
 */

public class DataManger {
    private Myshard mSharedPreferences;

    public DataManger(Myshard mSharedPreferences){
        this.mSharedPreferences=mSharedPreferences;
    }

    public User getUser(){
        return mSharedPreferences.getUser();
    }
    public void clear(){
        mSharedPreferences.clear();
    }

    public void setName(String name ){
        mSharedPreferences.putName(name);
    }
    public void setEmail(String email){
        mSharedPreferences.putEmail(email);
    }

    public void setIMG(String img){
        mSharedPreferences.putIMG(img);
    }

    public void setStatus(String status){
        mSharedPreferences.putStatus(status);
    }
    public void setUser(String name , String email ,String img,String status){
        mSharedPreferences.putUser(name,email,img,status);
    }
}
