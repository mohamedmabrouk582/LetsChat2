package com.example.mohamed.letschat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.userInfo.UserInfoViewPresenter;
import com.example.mohamed.letschat.view.UserInfoView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/11/2017.  time :16:38
 */

public class UserInfoFragment extends Fragment implements UserInfoView,View.OnClickListener{
    private static final String USER = "USER";
    private UserInfoViewPresenter presenter;
    private View  view;
    private CircleImageView userIMG;
    private TextView userStatus;
    private Button  sendButton,rejectButton;
    private User mUser;
    private ProgressBar userIMGloader;

    public static UserInfoFragment nerwFragment(User user){
        Bundle bundle=new Bundle();
        bundle.putParcelable(USER,user);
        UserInfoFragment  fragment=new UserInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.users_info_view,container,false);
        presenter=new UserInfoViewPresenter(getActivity());
        presenter.attachView(this);
        ini();
        return view;
    }

    private void ini(){
        mUser=getArguments().getParcelable(USER);
        userIMG=view.findViewById(R.id.other_user_img);
        userStatus=view.findViewById(R.id.other_user_status);
        sendButton=view.findViewById(R.id.send_confirm_friend);
        rejectButton=view.findViewById(R.id.reject);
        userIMGloader=view.findViewById(R.id.user_img_loader);

        rejectButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        setData(mUser);

    }

    private void setData(User data){
        Glide.with(getActivity()).load(data.getImageUrl()).error(R.drawable.logo).into(userIMG);
        userStatus.setText(data.getStatus());

    }


    @Override
    public String sendFriendRequest() {
        return null;
    }

    @Override
    public String rejectFriendRequest() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_confirm_friend:
                Toast.makeText(getActivity(), "send", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reject:
                Toast.makeText(getActivity(), "reject", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
