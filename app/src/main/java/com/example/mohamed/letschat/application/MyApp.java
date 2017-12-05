package com.example.mohamed.letschat.application;

import android.app.Application;
import android.util.Log;

import com.example.mohamed.letschat.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 20/11/2017.  time :00:34
 */

public class MyApp extends Application {
   static   FirebaseAuth mAuth;
   static DatabaseReference mDatabaseReference;
   static StorageReference mStorageReference;
   DataManger manger;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        Myshard myshard=new Myshard(this);
        manger=new DataManger(myshard);
        Log.d("token", FirebaseInstanceId.getInstance().getToken()+ "");
       // updateServerLisner();
    }

    private void updateServerLisner(){
        if (mAuth.getCurrentUser()==null) return;
        mDatabaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                manger.clear();
                manger.setUser(user.getName(),user.getEmail(),user.getImageUrl(),user.getStatus(),FirebaseInstanceId.getInstance().getToken());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public  DataManger getDataManger(){
        return manger;
    }

    public static StorageReference getStorageReference(){
        return mStorageReference;
    }
    public static DatabaseReference getDatabaseReference(){
        return mDatabaseReference;
    }

    public static FirebaseAuth getmAuth(){
        return mAuth;
    }
}
