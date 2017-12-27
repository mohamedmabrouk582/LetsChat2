package com.example.mohamed.letschat.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.userInfo.UserInfoPresenter;
import com.example.mohamed.letschat.presenter.userInfo.UserInfoViewPresenter;
import com.example.mohamed.letschat.utils.ZoomIMG;
import com.example.mohamed.letschat.view.UserInfoView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/11/2017.  time :16:38
 */

public class UserInfoFragment extends Fragment implements UserInfoView,View.OnClickListener{
    private static final String USER = "USER";
    private static final String USERKEY = "userKey";
    private static final String ME = "me";
    private UserInfoViewPresenter presenter;
    private View  view;
    private FrameLayout zoomContainer;
    private ImageView imgPreview;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    private CircleImageView userIMG;
    private TextView userStatus,userName;
    private Button  sendButton,rejectButton;
    private User mUser;
    private ProgressBar userIMGloader;
    private String userKey;
    private String mCurrent_state;
    private boolean me;
    private ZoomIMG zoomIMG;


    public static UserInfoFragment nerwFragment(User user,String userKey,boolean me){
        Bundle bundle=new Bundle();
        bundle.putParcelable(USER,user);
        bundle.putSerializable(USERKEY,userKey);
        bundle.putBoolean(ME,me);
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
        mCurrent_state="not_friends";
        ini();
        return view;
    }

    private void ini(){
        mUser=getArguments().getParcelable(USER);
        userKey=getArguments().getString(USERKEY);
        me=getArguments().getBoolean(ME);
        userIMG=view.findViewById(R.id.other_user_img);
        userStatus=view.findViewById(R.id.other_user_status);
        userName=view.findViewById(R.id.other_user_name);
        sendButton=view.findViewById(R.id.send_confirm_friend);
        rejectButton=view.findViewById(R.id.reject);
        userIMGloader=view.findViewById(R.id.user_img_loader);
        zoomContainer=view.findViewById(R.id.zoomContainer_preview);
        imgPreview=view.findViewById(R.id.user_img_preview);
        zoomIMG=new ZoomIMG();
        rejectButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        userIMG.setOnClickListener(this);
        setData(mUser);
        if (me){
            rejectButton.setVisibility(View.GONE);
            sendButton.setVisibility(View.GONE);
        }
        presenter.stateRequest(userKey, new UserInfoPresenter.requestListner() {
            @Override
            public void onSucess(String type) {
                CurrentState(type);
                Log.d("value", type + "");
            }
        });

    }

    private void setData(User data){
        Glide.with(getActivity()).load(data.getImageUrl()).into(userIMG);
        userStatus.setText(data.getStatus());
        userName.setText(data.getName());

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_confirm_friend:
                presenter.sendFriendRequest(userKey, mCurrent_state,mUser, new UserInfoPresenter.requestListner() {
                    @Override
                    public void onSucess(String type) {
                       sendButton.setText(type);
                       CurrentState(type);
                    }
                });
                break;
            case R.id.reject:
                presenter.reject(userKey, new UserInfoPresenter.requestListner() {
                    @Override
                    public void onSucess(String type) {
                        sendButton.setText(type);
                        CurrentState(type);
                    }
                });

                break;
            case R.id.other_user_img:
                mShortAnimationDuration = getResources().getInteger(
                        android.R.integer.config_shortAnimTime);
                zoomIMG.zoomImageFromThumb(getActivity(),userIMG,mUser.getImageUrl(),mCurrentAnimator,imgPreview,zoomContainer,mShortAnimationDuration);
                break;

        }
    }

    private void CurrentState(String type){
        rejectButton.setVisibility(View.GONE);
        switch (type){
            case "Remove Request":
                mCurrent_state="sent";
                break;
            case "Send Friend Request":
                mCurrent_state="not_friends";
                break;
            case "sent":
                mCurrent_state="sent";
                sendButton.setText("Remove Request");
                break;
            case "received":
                mCurrent_state="received";
                rejectButton.setVisibility(View.VISIBLE);
                rejectButton.setText(getString(R.string.reject));
                sendButton.setText(getString(R.string.accept));
                break;
            case "friends":
                mCurrent_state="friends";
                sendButton.setText(getString(R.string.unFriend));
                break;
            default:
                mCurrent_state="not_friends";
                sendButton.setText("Send Friend Request");
                break;
        }
    }

    @Override
    public String showIMG() {
        return null;
    }
}
