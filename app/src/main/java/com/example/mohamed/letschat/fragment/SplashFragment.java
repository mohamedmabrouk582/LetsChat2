package com.example.mohamed.letschat.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.activity.HomeActivity;
import com.example.mohamed.letschat.activity.LoginActivity;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.SplashViewPresenter;
import com.example.mohamed.letschat.view.SplashView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :19:35
 */

public class SplashFragment extends Fragment implements SplashView{

    private View view;
    private FirebaseAuth mAuth;
    private SplashViewPresenter presenter;

    public static SplashFragment newFragment(){
        return new SplashFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.splash_frgment,container,false);
       ini();
        selectActivity();
            return view;
     }

     private void ini(){
         presenter=new SplashViewPresenter(getActivity());
         presenter.attachView(this);
         mAuth=FirebaseAuth.getInstance();
     }


    @Override
    public void selectActivity() {
        FirebaseUser user=mAuth.getCurrentUser();
        if (user!=null){
            presenter.openHomeActivity(new User());
        }else {
            presenter.openLoginActivity();
        }
        getActivity().finish();
    }
}
