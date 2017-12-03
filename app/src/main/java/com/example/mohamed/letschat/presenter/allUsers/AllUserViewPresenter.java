package com.example.mohamed.letschat.presenter.allUsers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.activity.UserInfoActivity;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.BasePresenter;
import com.example.mohamed.letschat.view.AllUsersView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 28/11/2017.  time :00:03
 */

public class AllUserViewPresenter<v extends AllUsersView> extends BasePresenter<v> implements AllUserPresenter<v> {

    private Activity  activity;

     public AllUserViewPresenter(Activity activity){
      this.activity=activity;

    }

    @Override
    public void userClick(User user,String userKey,boolean me) {
        UserInfoActivity.start(activity,user,userKey,me);
    }
}
