package com.example.mohamed.letschat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.activity.LoginActivity;
import com.example.mohamed.letschat.presenter.register.RegisterViewPresenter;
import com.example.mohamed.letschat.view.RegisterView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :23:09
 */

public class RegisterFragment extends Fragment implements RegisterView,View.OnClickListener {
    private RegisterViewPresenter presenter;
    private EditText userName,email,password;
    private TextView login;
    private Button register;
    private View view;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.register,container,false);
        presenter=new RegisterViewPresenter(view,getActivity());
        presenter.attachView(this);
        ini();
        return view;
    }

    private void ini() {
        userName=view.findViewById(R.id.username);
        email=view.findViewById(R.id.email_register);
        password=view.findViewById(R.id.password_register);
        login=view.findViewById(R.id.txt_login);
        register=view.findViewById(R.id.but_register);
        progressBar=view.findViewById(R.id.register_progressBar);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void register(String userName, String email, String password) {
    presenter.register(userName,email,password);
    }

    @Override
    public void login() {
        LoginActivity.Start(getActivity());
        getActivity().finish();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showError(String msg) {
        Snackbar snackbar = Snackbar
                .make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_login:
                login();
                break;
            case R.id.but_register:
              register(userName.getText().toString(),email.getText().toString(),password.getText().toString());
                break;

        }
    }


}
