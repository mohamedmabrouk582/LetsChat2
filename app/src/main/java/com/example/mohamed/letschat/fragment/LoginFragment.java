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
import com.example.mohamed.letschat.activity.RegisterActivity;
import com.example.mohamed.letschat.presenter.login.LoginViewPresenter;
import com.example.mohamed.letschat.view.LoginView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :22:42
 */

public class LoginFragment extends Fragment implements LoginView,View.OnClickListener {
    private View  view;
    private EditText email,password;
    private Button login;
    private TextView createAccount;
    private LoginViewPresenter presenter;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     view=inflater.inflate(R.layout.login_fragment,container,false);
     presenter=new LoginViewPresenter(view,getActivity());
     presenter.attachView(this);
     ini();
     return view;
    }

    private void  ini(){
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        login=view.findViewById(R.id.but_login);
        createAccount=view.findViewById(R.id.create_account);
        progressBar=view.findViewById(R.id.login_progressBar);

        login.setOnClickListener(this);
        createAccount.setOnClickListener(this);
    }

    @Override
    public void login(String email ,String password) {
      presenter.login(email,password);

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
    public void createAccount() {
     //   go to register activity
        RegisterActivity.Start(getActivity());
        getActivity().finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.but_login:
                String eml=email.getText().toString();
                String pass=password.getText().toString();
                login(eml,pass);
                break;
            case R.id.create_account:
                createAccount();
                break;
        }
    }
}
