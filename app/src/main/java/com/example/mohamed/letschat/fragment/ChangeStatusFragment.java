package com.example.mohamed.letschat.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.presenter.changeStatus.ChangeStatusViewPresenrter;
import com.example.mohamed.letschat.view.ChangeStatusView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 27/11/2017.  time :22:52
 */

public class ChangeStatusFragment extends DialogFragment implements ChangeStatusView,View.OnClickListener{

    private static final String STATUS = "status";
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private View view;
    private Button save,cancel;
    private EditText statusEditText;
    private ChangeStatusViewPresenrter presenrter;
    DataManger  dataManger;
    public static ChangeStatusFragment newFragment(){
        ChangeStatusFragment fragment=new ChangeStatusFragment();

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view= LayoutInflater.from(getActivity()).inflate(R.layout.edit_status,null);
        ini();
        presenrter=new ChangeStatusViewPresenrter(getActivity(),statusEditText,dialog,dataManger);
        presenrter.attachView(this);
        return dialog;
    }

    private void ini(){
        dataManger=((MyApp) getActivity().getApplication()).getDataManger();
     statusEditText=view.findViewById(R.id.edt_status);
     save=view.findViewById(R.id.save);
     cancel=view.findViewById(R.id.cancel);
     builder=new AlertDialog.Builder(getActivity());
     builder.setView(view);
     dialog= builder.create();
     statusEditText.setText(dataManger.getUser().getStatus());
     dialog.setCanceledOnTouchOutside(false);
     cancel.setOnClickListener(this);
     save.setOnClickListener(this);
    }

    @Override
    public void cancel() {
      dialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save:
                String status=statusEditText.getText().toString();
                if (TextUtils.isEmpty(status)){
                    presenrter.showSnakBar("Status not Allow to be Empty ",view);
                    YoYo.with(Techniques.Shake).playOn(statusEditText);
                }else {
                    presenrter.save(status);
                }
                break;
            case R.id.cancel:
                cancel();
                break;
        }
    }
}
