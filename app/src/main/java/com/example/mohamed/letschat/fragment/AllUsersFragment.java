package com.example.mohamed.letschat.fragment;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.allUsers.AllUserViewPresenter;
import com.example.mohamed.letschat.view.AllUsersView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 28/11/2017.  time :00:41
 */

public class AllUsersFragment extends Fragment implements AllUsersView{
    private View  view;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AllUserViewPresenter presenter;
    private FirebaseRecyclerAdapter adapter;
    private DatabaseReference mDatabaseReference;
    private Query query;
    int count=0;
    static int num=1;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.all_users,container,false);
        presenter=new AllUserViewPresenter(getActivity());
        presenter.attachView(this);
        mDatabaseReference= MyApp.getDatabaseReference();
        query=mDatabaseReference.child("Users").limitToFirst(10);
        iniRecyl();
        iniSwipe();
        showUsers();
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
        adapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void showUserClickedMessage(User user,String userKey,boolean me) {
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
        final FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();

        Log.d("model",  options.getSnapshots()+ "");
        adapter=new FirebaseRecyclerAdapter<User,UsersHolder>(options) {
            @Override
            public UsersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                hideProgress();
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.user_view, parent, false);
                Log.d("model",  options.getSnapshots()+ "");

                count ++;

                if (options.getSnapshots().size()==count){
                     count=0;
                 }

                 if (options.getSnapshots().get(count).getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                     Log.d("equal", "equal"+ "");
                 }else {

                 }

                return new UsersHolder(view);
            }

            @Override
            protected void onBindViewHolder(UsersHolder holder, final int position, final User model) {
                holder.bind(model);
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showUserClickedMessage(model,String.valueOf(options.getSnapshots().getSnapshot(position).getKey()),
                                options.getSnapshots().getSnapshot(position).getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                ?true:false
                                );
                        //Log.d("key", options.getSnapshots().getSnapshot(position).getKey()+ "");

                    }
                });
            }
        };

        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


       class UsersHolder extends RecyclerView.ViewHolder{
        private CircleImageView userIMG;
        private TextView userName, userStatus;
        public View view;
        private ImageView online;
        @SuppressLint("WrongViewCast")
        public UsersHolder(View itemView) {
            super(itemView);
            view=itemView;
            userIMG=itemView.findViewById(R.id.user_img_status);
            userName=itemView.findViewById(R.id.user_name);
            userStatus=itemView.findViewById(R.id.user_status);
            online=itemView.findViewById(R.id.user_online);

        }

        public void bind(User user){
            //if (user.getImageUrl().length()>10){
            online.setImageResource(user.isOnline()?R.drawable.ic_online:R.drawable.ic_offline);
            Glide.with(getActivity()).load(user.getImageUrl()).error(R.drawable.logo)
                    .into(userIMG);
            // }

            userStatus.setText(user.getStatus());
            userName.setText(user.getName());
        }
    }
}
