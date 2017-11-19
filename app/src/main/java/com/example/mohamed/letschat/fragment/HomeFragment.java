package com.example.mohamed.letschat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.adapter.PagerAdapter;
import com.example.mohamed.letschat.presenter.HomeViewPresenter;
import com.example.mohamed.letschat.view.HomeView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 19/11/2017.  time :19:38
 */

public class HomeFragment extends Fragment implements HomeView{
    private HomeViewPresenter presenter;
    private View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    public static HomeFragment newFragment(){
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_fragment,container,false);
        presenter=new HomeViewPresenter(getActivity());
        presenter.attachView(this);
        ini();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.all_friends:

                return true;
            case R.id.setting_profile:
                return true;
            case R.id.logout:
              presenter.logout();
            return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void ini(){
      mTabLayout=view.findViewById(R.id.tabs);
      mViewPager=view.findViewById(R.id.tabs_pager);
      mTabLayout.setupWithViewPager(mViewPager);
      mViewPager.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager()));
    }



}
