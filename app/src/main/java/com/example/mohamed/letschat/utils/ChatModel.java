package com.example.mohamed.letschat.utils;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 27/11/2017.  time :18:57
 */

public class ChatModel {

    private static ChatModel ourInstance=null;

    public interface ChatListener{
        void onUpdateMSG(String msg);
        void logout();
    }
   private ChatListener listener;
    public static ChatModel getInstance( ) {
        if (ourInstance==null){
            ourInstance=new ChatModel();
        }
        return ourInstance;
    }



    public void setListener( ChatListener listener){
        this.listener = listener ;
    }

    public void notifyMsg(String s){
        if (listener!= null)
            listener.onUpdateMSG(s);
    }

    public void logout(){
        if (listener!= null)
            listener.logout();
    }




}
