package com.example.mohamed.letschat.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.Friend;
import com.example.mohamed.letschat.data.Request;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.friends.FriendsPresenter;
import com.example.mohamed.letschat.presenter.friends.FriendsViewPresenter;
import com.example.mohamed.letschat.presenter.requests.RequestViewPresenter;
import com.example.mohamed.letschat.view.FriendsView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :21:51
 */

public class FriendsFragment extends Fragment implements FriendsView {
    private View view;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatabaseReference mDatabaseReference;
    private FriendsViewPresenter presenter;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter adapter;
    private Query query;
    private DataManger dataManger;
    private String mCurrentState="sent";
    private User user;
    private DatabaseReference mReference;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.friends_fragment,container,false);
        presenter=new FriendsViewPresenter(getActivity());
        presenter.attachView(this);
        dataManger=((MyApp) getActivity().getApplication()).getDataManger();
        mAuth= MyApp.getmAuth();
        mDatabaseReference= MyApp.getDatabaseReference().child("Friends").child(mAuth.getCurrentUser().getUid());
        mReference=MyApp.getDatabaseReference().child("Users");
        query=mDatabaseReference.limitToFirst(10);
        iniRecyl();
        iniSwipe();
        showUsers();
        Log.d("me", "friends" + "");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }



    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void iniRecyl(){
        mRecyclerView=view.findViewById(R.id.users_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void iniSwipe(){
        mSwipeRefreshLayout=view.findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showUsers();
                adapter.startListening();
            }
        });
    }

    @Override
    public void showProgress() {
        if (!mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void hideProgress() {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showUserClickedMessage(User user, String userKey,boolean me) {
        presenter.userClick(user,userKey,me);

    }


    @Override
    public void showUsers() {
        showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
            }
        },1000l);
        final FirebaseRecyclerOptions<String> options =
                new FirebaseRecyclerOptions.Builder<String>()
                        .setQuery(query, String.class)
                        .build();

        adapter=new FirebaseRecyclerAdapter<String,FriendsHolder>(options) {
            @Override
            public FriendsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(getActivity()).inflate(R.layout.friend_item_view,parent,false);
                return new FriendsHolder(view);
            }

            @Override
            protected void onBindViewHolder(final FriendsHolder holder, final int position, final String model) {




                mReference.child(options.getSnapshots().getSnapshot(position).getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      final User user=dataSnapshot.getValue(User.class);
                        try {
                              holder.bind(user,model);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d("datejj", model + "");



                      holder.unFriend.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              try {
                                  presenter.unFriend(options.getSnapshots().getSnapshot(position).getKey(), new FriendsPresenter.requestListner() {
                                      @Override
                                      public void onSucess() {
                                          adapter.notifyDataSetChanged();
                                      }
                                  });
                              }catch (Exception e){
                                  presenter.unFriend(options.getSnapshots().getSnapshot(position-1).getKey(), new FriendsPresenter.requestListner() {
                                      @Override
                                      public void onSucess() {
                                          adapter.notifyDataSetChanged();
                                      }
                                  });
                              }

                          }
                      });

                      holder.view.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              try {
                                  presenter.userClick(user,options.getSnapshots().getSnapshot(position).getKey(), options.getSnapshots().getSnapshot(position).getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                          ?true:false
                                  );
                              }catch (Exception e){
                                  presenter.userClick(user,options.getSnapshots().getSnapshot(position-1).getKey(), options.getSnapshots().getSnapshot(position-1).getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                          ?true:false
                                  );
                              }

                          }
                      });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };

        mRecyclerView.setAdapter(adapter);
    }

    class FriendsHolder extends RecyclerView.ViewHolder{
        private CircleImageView userIMG;
        private TextView userName,userStatus;
        public View view;
        public Button unFriend;
        private ImageView online;
        @SuppressLint("WrongViewCast")
        public FriendsHolder(View itemView) {
            super(itemView);
            view=itemView;
            userIMG=itemView.findViewById(R.id.user_img_status_friend);
            userName=itemView.findViewById(R.id.user_name_friend);
            userStatus=itemView.findViewById(R.id.friend_status);
            unFriend=itemView.findViewById(R.id.un_friend);
            online=itemView.findViewById(R.id.friends_online);

        }

        public void bind(User user,String date) throws ParseException {
            if (getActivity()!=null && user !=null){
            Glide.with(getActivity()).load(user.getImageUrl())
                    .into(userIMG);
            userName.setText(user.getName());
            online.setImageResource(user.isOnline()?R.drawable.ic_online:R.drawable.ic_offline);
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
                Date eDate = sdf.parse(date);
                userStatus.setText(String.valueOf(eDate));
            }
        }
    }
}
