package com.example.mohamed.letschat.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.adapter.PagerAdapter;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.presenter.home.HomeViewPresenter;
import com.example.mohamed.letschat.view.HomeView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeView {
    private static final String USER ="USER" ;
    private static final int IMG = 0;
    private HomeViewPresenter presenter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CircleImageView mProfileImge;
    private ImageView edtIMG;
    private TextView mProfilrName,mProfileEmail;
    private User mUser;
    public static void Start(Context context,User user){
        Intent intent=new Intent(context,HomeActivity.class);
        intent.putExtra(USER,user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        iniHeadView(navigationView.getHeaderView(0));

        /**********/
        mUser=getIntent().getParcelableExtra(USER);
        presenter=new HomeViewPresenter(this,navigationView.getHeaderView(0));
        presenter.attachView(this);
        ini();
        setData(mUser);

    }

    private void iniHeadView(View view){
        mProfileEmail=view.findViewById(R.id.profile_email);
        mProfileImge=view.findViewById(R.id.profile_img);
        mProfilrName=view.findViewById(R.id.profile_name);
        edtIMG=view.findViewById(R.id.edit_img);
        edtIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editIMG();
            }
        });
    }

    private void setData(User data){
        mProfilrName.setText(data.getName());
        mProfileEmail.setText(data.getEmail());
        if (!data.getImageUrl().equals("default")){
            Picasso.with(this).load(Uri.parse(data.getImageUrl())).placeholder(R.drawable.logo)
                    .into(mProfileImge, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                        }
                    });
        }
    }

    private void ini(){
        mTabLayout=findViewById(R.id.tabs);
        mViewPager=findViewById(R.id.tabs_pager);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void editIMG() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Choose Your photo"),IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMG && resultCode==RESULT_OK){
            presenter.edtIMG(data.getData(),mProfileImge);
        }
    }
}
