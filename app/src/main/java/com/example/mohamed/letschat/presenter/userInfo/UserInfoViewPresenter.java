package com.example.mohamed.letschat.presenter.userInfo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.UserInfoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/11/2017.  time :16:15
 */

public class UserInfoViewPresenter<v extends UserInfoView> extends BasePresenter<v> implements UserInfoPresenter<v> {

    private Activity activity;
    private DataManger dataManger;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference databaseReference;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    public UserInfoViewPresenter(Activity activity){
        this.activity=activity;
        mDatabaseReference= MyApp.getDatabaseReference().child("Friends_req");
        databaseReference=MyApp.getDatabaseReference().child("Friends");
        mReference=MyApp.getDatabaseReference();
        mAuth=MyApp.getmAuth();
        dataManger=((MyApp) activity.getApplication()).getDataManger();
    }


    @Override
    public void sendFriendRequest(final String userid, String requestType, final User user, final requestListner listner) {



      switch (requestType){
          case "not_friends":
              String notificationId=FirebaseDatabase.getInstance().getReference().child("notifications").push().getKey();
              Map<String,String> notificationData=new HashMap<>();
              notificationData.put("from",mAuth.getCurrentUser().getUid());
              notificationData.put("msg","nothing");
              notificationData.put("type","request");
              Map requestMap=new HashMap<>();
              requestMap.put("Friends_req/"+mAuth.getCurrentUser().getUid()+"/"+userid+"/request_type","sent");
              requestMap.put("Friends_req/"+userid+"/"+mAuth.getCurrentUser().getUid()+"/request_type","received");
              mReference.updateChildren(requestMap, new DatabaseReference.CompletionListener() {
                  @Override
                  public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError==null){
                        Log.d("hh", "uuu" + "");
                          // at notify type 1 (request friend)
                         String notificationData=
                                 "{'contents': {'en': '"+dataManger.getUser().getName()+" has sent you request'}," +
                                "'include_player_ids': ['"+user.getDevice_token()+"'], " +
                                "'headings': {'en': 'New Friend Request'}, " +
                                "'data' :{'from':'"+mAuth.getCurrentUser().getUid()+"' , 'type':'1'} }";
                        try {
                            OneSignal.postNotification(new JSONObject(notificationData), new OneSignal.PostNotificationResponseHandler() {
                                @Override
                                public void onSuccess(JSONObject response) {
                                    listner.onSucess("Remove Request");
                                }

                                @Override
                                public void onFailure(JSONObject response) {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                  }
              });

              break;
          case "sent":
              Map sentMap=new HashMap<>();
              sentMap.put("Friends_req/"+mAuth.getCurrentUser().getUid()+"/"+userid+"/request_type",null);
              sentMap.put("Friends_req/"+userid+"/"+mAuth.getCurrentUser().getUid()+"/request_type",null);

              mReference.updateChildren(sentMap, new DatabaseReference.CompletionListener() {
                  @Override
                  public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                      if (databaseError==null){
                          listner.onSucess("Send Friend Request");
                      }
                  }
              });
              break;
          case "received":
              String date= DateFormat.getDateTimeInstance().format(new Date());
              Map receivedMap=new HashMap<>();
              receivedMap.put("Friends_req/"+mAuth.getCurrentUser().getUid()+"/"+userid+"/request_type",null);
              receivedMap.put("Friends_req/"+userid+"/"+mAuth.getCurrentUser().getUid()+"/request_type",null);

              receivedMap.put("Friends/"+mAuth.getCurrentUser().getUid()+"/"+userid,date);
              receivedMap.put("Friends/"+userid+"/"+mAuth.getCurrentUser().getUid(),date);

              mReference.updateChildren(receivedMap, new DatabaseReference.CompletionListener() {
                  @Override
                  public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                      if (databaseError==null){
                          Log.d("change", "ff" + "");
                          listner.onSucess("friends");
                      }
                  }
              });
              break;
          case "friends":
              Map friendsMap=new HashMap();
              friendsMap.put("Friends/"+mAuth.getCurrentUser().getUid()+"/"+userid,null);
              friendsMap.put("Friends/"+userid+"/"+mAuth.getCurrentUser().getUid(),null);

              mReference.updateChildren(friendsMap, new DatabaseReference.CompletionListener() {
                  @Override
                  public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                      listner.onSucess("Send Friend Request");
                  }
              });
              break;

      }

    }



    @Override
    public void stateRequest(final String userid, final requestListner listner) {
     mDatabaseReference.child(mAuth.getCurrentUser().getUid()).child(userid).child("request_type").addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             Log.d("change", "ff" + "");
             try {
                 listner.onSucess(String.valueOf(dataSnapshot.getValue().toString()));
             }catch (Exception e){
                 databaseReference.child(mAuth.getCurrentUser().getUid()).child(userid).addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         try {
                             listner.onSucess(TextUtils.isEmpty(dataSnapshot.getValue().toString())?"not":"friends");
                         }catch (Exception e){
                             listner.onSucess("not");
                         }
                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });
             }
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     })  ;
    }

    @Override
    public void reject(final String userid, final requestListner listner) {
        Map rejectMap=new HashMap();
        rejectMap.put("Friends_req/"+mAuth.getCurrentUser().getUid()+"/"+userid+"/request_type",null);
        rejectMap.put("Friends_req/"+userid+"/"+mAuth.getCurrentUser().getUid()+"/request_type",null);

        mReference.updateChildren(rejectMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError==null){
                    Log.d("change", "ff" + "");
                    listner.onSucess("Send Friend Request");

                }
            }
        });
    }


}
