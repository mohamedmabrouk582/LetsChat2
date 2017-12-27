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
import com.example.mohamed.letschat.data.Request;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.requests.RequestPresenter;
import com.example.mohamed.letschat.presenter.requests.RequestViewPresenter;
import com.example.mohamed.letschat.view.RequestView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :21:51
 */

public class RequestsFragment extends Fragment implements RequestView {
    private View view;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatabaseReference mDatabaseReference;
    private RequestViewPresenter presenter;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter adapter;
    private Query query;
    private String mCurrentState="sent";
    private DatabaseReference mReference;
    private boolean acceptFlag,cancelFlag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.requests_frgament,container,false);
         presenter=new RequestViewPresenter(getActivity());
         presenter.attachView(this);
        mAuth=MyApp.getmAuth();
        mDatabaseReference= MyApp.getDatabaseReference().child("Friends_req").child(mAuth.getCurrentUser().getUid());
        mReference=MyApp.getDatabaseReference().child("Users");
        query=mDatabaseReference.limitToFirst(10);
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
        final FirebaseRecyclerOptions<Request> options =
                new FirebaseRecyclerOptions.Builder<Request>()
                        .setQuery(query, Request.class)
                        .build();
        adapter=new FirebaseRecyclerAdapter<Request,RequestsHolder>(options) {
            @Override
            public RequestsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(getActivity()).inflate(R.layout.request_item_view,parent,false);

                return new RequestsHolder(view);
            }
            //TODO

            @Override
            protected void onBindViewHolder(final RequestsHolder holder, final int position, final Request model) {
//

              mReference.child(options.getSnapshots().getSnapshot(position).getKey()).addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      final User user=dataSnapshot.getValue(User.class);
                      holder.bind(user,model);

                      holder.accept.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              try {
                                  presenter.acceptCancelRequest(options.getSnapshots().getSnapshot(position).getKey(), model.getRequest_type(), new RequestPresenter.requestListner() {
                                      @Override
                                      public void onSucess() {
                                          adapter.notifyDataSetChanged();

                                      }
                                  });

                              }catch (Exception e){
                                  acceptFlag=true;
                              presenter.acceptCancelRequest(options.getSnapshots().getSnapshot(position - 1).getKey(), model.getRequest_type(), new RequestPresenter.requestListner() {
                                  @Override
                                  public void onSucess() {
                                      adapter.notifyDataSetChanged();
                                  }
                              });
                          }
                          }
                      });

                      holder.cancel.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              try {
                                      presenter.acceptCancelRequest(options.getSnapshots().getSnapshot(position).getKey(), "sent", new RequestPresenter.requestListner() {
                                          @Override
                                          public void onSucess() {
                                              adapter.notifyDataSetChanged();

                                          }
                                      });
                              }catch (Exception e){
                                  cancelFlag=true;
                                  presenter.acceptCancelRequest(options.getSnapshots().getSnapshot(position - 1).getKey(), "sent", new RequestPresenter.requestListner() {
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
                                  presenter.userClick(user,options.getSnapshots().getSnapshot(position).getKey(),
                                          options.getSnapshots().getSnapshot(position).getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                  ?true:false
                                          );
                              }catch (Exception  e){
                                  presenter.userClick(user,options.getSnapshots().getSnapshot(position-1).getKey()
                                  , options.getSnapshots().getSnapshot(position).getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())
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
    adapter.notifyDataSetChanged();
    }


    class RequestsHolder extends RecyclerView.ViewHolder{
        private CircleImageView userIMG;
        private TextView userName;
        public View view;
        public Button accept,cancel;
        private ImageView online;
        @SuppressLint("WrongViewCast")
        public RequestsHolder(View itemView) {
            super(itemView);
            view=itemView;
            userIMG=itemView.findViewById(R.id.user_img_status_req);
            userName=itemView.findViewById(R.id.user_name_req);
            accept=itemView.findViewById(R.id.accept_req);
            cancel=itemView.findViewById(R.id.cancel_req);
            online=itemView.findViewById(R.id.request_online);

        }

        public void bind(User user,Request request){
            try {
                String req_type=request.getRequest_type();
                online.setImageResource(user.isOnline()?R.drawable.ic_online:R.drawable.ic_offline);
                Glide.with(getActivity()).load(user.getImageUrl())
                        .into(userIMG);
                switch (req_type){
                    case "sent":
                        mCurrentState="sent";
                        accept.setVisibility(View.GONE);
                        break;
                    case "received":
                        mCurrentState="received";
                        accept.setVisibility(View.VISIBLE);
                        break;
                }
                userName.setText(user.getName());
            }catch (Exception  e){

            }

        }
    }
}
