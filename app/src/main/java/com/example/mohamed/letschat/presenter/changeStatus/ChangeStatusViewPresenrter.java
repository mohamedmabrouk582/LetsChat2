package com.example.mohamed.letschat.presenter.changeStatus;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.ChangeStatusView;
import com.example.mohamed.letschat.view.MainView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.logging.Handler;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 27/11/2017.  time :22:35
 */

public class ChangeStatusViewPresenrter<v extends ChangeStatusView> extends BasePresenter<v> implements ChangeStatusPresenter<v> {

    private Activity activity;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth  mAuth;
    private View view;
    private AlertDialog dialog;
    private DataManger dataManger;
    public ChangeStatusViewPresenrter(Activity activity, View view, AlertDialog dialog,DataManger dataManger){
        this.activity=activity;
        mDatabaseReference=MyApp.getDatabaseReference();
        mAuth=MyApp.getmAuth();
        this.dataManger=dataManger;
        this.view=view;
        this.dialog=dialog;
    }
    @Override
    public void save(final String status) {
     mDatabaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("status").setValue(status).addOnSuccessListener(new OnSuccessListener<Void>() {
         @Override
         public void onSuccess(Void aVoid) {
             showSnakBar("Update Status",view);
             dataManger.setStatus(status);
             new android.os.Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                  dialog.dismiss();
                 }
             },1000);


         }
     });
    }
}
