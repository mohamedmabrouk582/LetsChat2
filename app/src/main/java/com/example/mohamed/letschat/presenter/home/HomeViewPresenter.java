package com.example.mohamed.letschat.presenter.home;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.activity.SplashActivity;
import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.HomeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :20:52
 */

public class HomeViewPresenter<v extends HomeView> extends BasePresenter<v> implements HomePresenter<v> {
    private Activity context;
    private DataManger dataManger;
    private StorageReference mStorageReference;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private ProgressBar loadProgressBar;
    private TextView txtLoad;
    public HomeViewPresenter(Activity context,View view){
        this.context=context;
        dataManger=((MyApp) context.getApplication()).getDataManger();
        mStorageReference=MyApp.getStorageReference();
        mAuth=MyApp.getmAuth();
        mDatabaseReference=MyApp.getDatabaseReference();
        loadProgressBar=view.findViewById(R.id.img_load);
        txtLoad=view.findViewById(R.id.txt_progress);

    }


    @Override
    public void allFriends() {
        // go to all friends Activity
    }

    @Override
    public void settingsProfile() {
     // go to settings Activity
    }

    @Override
    public void edtIMG(final Uri uri, final CircleImageView view) {
        loadProgressBar.setVisibility(View.VISIBLE);
        txtLoad.setVisibility(View.VISIBLE);
     String root="Users/"+mAuth.getCurrentUser().getUid()+"/";
     mStorageReference.child(root).putFile(uri).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
             loadProgressBar.setVisibility(View.GONE);
             txtLoad.setVisibility(View.GONE);
             Toast.makeText(context, e.getMessage()+"", Toast.LENGTH_SHORT).show();
         }
     }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
         @Override
         public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
             double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
             txtLoad.setText(String.valueOf((int)progress));
             loadProgressBar.setProgress((int) progress);
         }
     })
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
         @Override
         public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
          mDatabaseReference.child("Users").child(mAuth.getCurrentUser()
                  .getUid()).child("imageUrl").setValue(String.valueOf(taskSnapshot.getDownloadUrl()))
                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                         dataManger.setIMG(String.valueOf(taskSnapshot.getDownloadUrl()));
                          loadProgressBar.setVisibility(View.GONE);
                          txtLoad.setVisibility(View.GONE);
                          view.setImageURI(uri);
                      }
                  });
         }
     });
    }

    @Override
    public void logout() {
        dataManger.clear();
        MyApp.getmAuth().signOut();
        SplashActivity.Start(context);
    }
}
