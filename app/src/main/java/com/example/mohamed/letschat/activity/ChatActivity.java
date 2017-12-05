package com.example.mohamed.letschat.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.Chat;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.chat.ChatPresenter;
import com.example.mohamed.letschat.presenter.chat.ChatViewPresenter;
import com.example.mohamed.letschat.utils.ToolBar;
import com.example.mohamed.letschat.view.ChatView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 04/12/2017.  time :21:28
 */

public class ChatActivity extends AppCompatActivity implements ChatView{
    private static final String USERKEY = "userkey";
    private static final String USER = "USER";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView send;
    private EditText massageEditText;
    private Query query;
    private FirebaseRecyclerAdapter adapter;
    private ChatViewPresenter presenter;
    private DatabaseReference mDatabaseReference;
    private String userKey;
    private User mUser;


    public static Intent newIntent(Context context,User user,String userKey){
        Intent intent=new Intent(context,ChatActivity.class);
        intent.putExtra(USERKEY,userKey);
        intent.putExtra(USER,user);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_fragment);
        presenter=new ChatViewPresenter(this);
        presenter.attachView(this);
        init();
        iniRecyl();
        iniSwipe();
    }

    private void init(){
        userKey=getIntent().getStringExtra(USERKEY);
        mUser=getIntent().getParcelableExtra(USER);
        send=findViewById(R.id.send);
        massageEditText=findViewById(R.id.massage);
        setToolbar();
        mDatabaseReference= MyApp.getDatabaseReference().child("Chats");
        query=mDatabaseReference.limitToFirst(50);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
    }

    private void setToolbar(){
         new ToolBar.ToolBarBuilder(mUser.getName(), this)
                .setImg(mUser.getImageUrl())
                .setIMGAction(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).setTitleAction(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).build();

    }

    private void iniRecyl(){
        mRecyclerView=findViewById(R.id.users_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void iniSwipe(){
        mSwipeRefreshLayout=findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showMassages();
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
    public void showMassages() {
        showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
            }
        },1000l);
        final FirebaseRecyclerOptions<Chat> options =
                new FirebaseRecyclerOptions.Builder<Chat>()
                        .setQuery(query, Chat.class)
                        .build();

        adapter=new FirebaseRecyclerAdapter<Chat,ChatHolder>(options){

            @Override
            public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(ChatActivity.this).inflate(R.layout.message_item,parent,false);
                return new ChatHolder(view);
            }

            @Override
            protected void onBindViewHolder(ChatHolder holder, int position, Chat model) {
               holder.bind(model);
            }
        };

        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void send() {
     ///// action
        String msg=massageEditText.getText().toString();
        if (TextUtils.isEmpty(msg)){

        }else {
            presenter.send(msg, userKey, new ChatPresenter.responseLisnter() {
                @Override
                public void sucess() {
                    massageEditText.setText(null);
                    massageEditText.setHint(R.string.massage);

//                    adapter.notifyDataSetChanged();
                }
            });

        }
    }


    class ChatHolder extends RecyclerView.ViewHolder{
        public View view;
        private LinearLayout send,received;
        private TextView send_msg,rec_msg,send_time,rec_time;

        @SuppressLint("WrongViewCast")
        public ChatHolder(View itemView) {
            super(itemView);
            view=itemView;
            send=itemView.findViewById(R.id.send_layout);
            received=itemView.findViewById(R.id.rec_layout);
            send_msg=itemView.findViewById(R.id.txt_msg_send);
            rec_msg=itemView.findViewById(R.id.txt_msg_rec);
            send_time=itemView.findViewById(R.id.txt_msg_time_send);
            rec_time=itemView.findViewById(R.id.txt_msg_time_rec);

        }

        public void bind(Chat chat){
           if (chat.getFrom().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
               // i  send
               received.setVisibility(View.GONE);
               send.setVisibility(View.VISIBLE);
               send_msg.setText(chat.getMsg());
               send_time.setText(chat.getDate());

           }else {
               // i received
               send.setVisibility(View.GONE);
               received.setVisibility(View.VISIBLE);
               rec_msg.setText(chat.getMsg());
               rec_time.setText(chat.getDate());
           }
        }
    }
}
