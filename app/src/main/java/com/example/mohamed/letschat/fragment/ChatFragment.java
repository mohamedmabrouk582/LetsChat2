package com.example.mohamed.letschat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :21:52
 */

public class ChatFragment extends Fragment{
    private DataManger dataManger;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.chat_fragment,container,false);
        dataManger=((MyApp) getActivity().getApplication()).getDataManger();
        return view;
    }


}
