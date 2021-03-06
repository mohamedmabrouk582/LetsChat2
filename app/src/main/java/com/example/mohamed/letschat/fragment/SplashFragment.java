package com.example.mohamed.letschat.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.presenter.splash.SplashViewPresenter;
import com.example.mohamed.letschat.view.SplashView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :19:35
 */

public class SplashFragment extends Fragment implements SplashView{

    private View view;
    private FirebaseAuth mAuth;
    private SplashViewPresenter presenter;
    private DataManger dataManger;
    private DatabaseReference mDatabaseReference;

    public static SplashFragment newFragment(){
        return new SplashFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.splash_frgment,container,false);
       ini();
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               selectActivity();
           }
       },1000);
            return view;
     }

     private void ini(){
         presenter=new SplashViewPresenter(getActivity());
         presenter.attachView(this);
         mAuth=FirebaseAuth.getInstance();
         dataManger=((MyApp) getActivity().getApplication()).getDataManger();
     }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void selectActivity() {
        FirebaseUser user=mAuth.getCurrentUser();
        if (user!=null){
            presenter.openHomeActivity();

        }else {
            dataManger.clear();
            presenter.openLoginActivity();
        }
        getActivity().finish();
    }

}
