package com.example.mohamed.letschat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.mohamed.letschat.R;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 05/12/2017.  time :00:12
 */

public class MyDialog extends Activity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.msg_dialog);
        findViewById(R.id.container).setOnClickListener(this);
        findViewById(R.id.btnOk).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        finish();
        // Exe.alertDialogInfo(this, "sadsadsa");

    }
}

