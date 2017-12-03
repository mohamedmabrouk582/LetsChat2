package com.example.mohamed.letschat.presenter.allUsers;

import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.base.MainPresenter;
import com.example.mohamed.letschat.view.AllUsersView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 27/11/2017.  time :23:51
 */

public interface AllUserPresenter<v extends AllUsersView>  extends MainPresenter<v>{
    void  userClick(User user,String userkey,boolean me);

}
