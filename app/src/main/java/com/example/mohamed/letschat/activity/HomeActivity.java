package com.example.mohamed.letschat.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mohamed.letschat.R;
import com.example.mohamed.letschat.adapter.PagerAdapter;
import com.example.mohamed.letschat.application.DataManger;
import com.example.mohamed.letschat.application.MyApp;
import com.example.mohamed.letschat.data.User;
import com.example.mohamed.letschat.fragment.ChangeStatusFragment;
import com.example.mohamed.letschat.presenter.home.HomePresenter;
import com.example.mohamed.letschat.presenter.home.HomeViewPresenter;
import com.example.mohamed.letschat.utils.ZoomIMG;
import com.example.mohamed.letschat.view.HomeView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeView,View.OnClickListener {
    private static final String USER ="USER" ;
    private static final int IMG = 0;
    private static final int PERMISSION = 100;
    private HomeViewPresenter presenter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CircleImageView mProfileImge;
    private ImageView edtIMG,img_preview;
    private TextView mProfilrName,mProfileEmail;
    private User mUser;
    private DataManger dataManger;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    private LinearLayout zoomContainer;
    private DrawerLayout drawer;
    private ZoomIMG zoomIMG;
    private String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static void Start(Context context){
        Intent intent=new Intent(context,HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        iniHeadView(navigationView.getHeaderView(0));

        /**********/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        }
        dataManger=((MyApp) getApplication()).getDataManger();
        mUser=dataManger.getUser();
        presenter=new HomeViewPresenter(this,navigationView.getHeaderView(0));
        presenter.attachView(this);
        zoomIMG=new ZoomIMG();
        ini();
        dataManger.setFragmnt("Requests");
        Log.d("tap", dataManger.getFragment() + "");

        setData(mUser);

    }

    private void iniHeadView(View view){
        mProfileEmail=view.findViewById(R.id.profile_email);
        mProfileImge=view.findViewById(R.id.profile_img);
        mProfilrName=view.findViewById(R.id.profile_name);
        edtIMG=view.findViewById(R.id.edit_img);
        edtIMG.setOnClickListener(this);
    }

    private void setData(User data){
        mProfilrName.setText(data.getName());
        mProfileEmail.setText(data.getEmail());
        if (!data.getImageUrl().equals("default")){
            Log.d("sdsd", data.getImageUrl() + "");
          //  mProfileImge.setImageURI(Uri.parse(data.getImageUrl()));

            Glide.with(this).load(data.getImageUrl()).error(R.drawable.logo)
                    .into(mProfileImge);
        }
    }

    private void ini(){
        mTabLayout=findViewById(R.id.tabs);
        mViewPager=findViewById(R.id.tabs_pager);
        img_preview=findViewById(R.id.img_preview);
        mTabLayout.setupWithViewPager(mViewPager);
        zoomContainer=findViewById(R.id.zoomContainer);
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                dataManger.setFragmnt(String.valueOf(tab.getText()));
                Log.d("tap", dataManger.getFragment() + "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mProfileImge.setOnClickListener(this);
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
               presenter.allFriends();
                return true;
            case R.id.logout:
                presenter.logout();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case    R.id.edit_status:
                FragmentManager fragmentManager=getSupportFragmentManager();
                ChangeStatusFragment  fragment=ChangeStatusFragment.newFragment();
                fragment.show(fragmentManager,"changeStatus");
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void editIMG() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

//        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        startActivityForResult(Intent.createChooser(intent,"Choose Your photo"),IMG);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void  checkPermissions(){
        try {
            for (int i = 0; i <permissions.length ; i++) {
                if (hasPermission(permissions[i])) {
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[i])) {
//
//                    } else {
//                        requestPermissions(new String[]{permissions[i]}, PERMISSION);
//                    }
                    requestPermissions(new String[]{permissions[i]}, PERMISSION);
                }
            }
        }catch (Exception e){}
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.d("uri", resultUri + "");
                presenter.edtIMG(resultUri, mProfileImge, new HomePresenter.update() {
                    @Override
                    public void sucess(User user) {
                        mUser=user;
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_img:
               editIMG();
                break;
            case R.id.profile_img:
                showIMG();
                break;
        }
    }

    @Override
    public void showIMG() {
        drawer.closeDrawers();
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        zoomIMG.zoomImageFromThumb(this,mProfileImge,mUser.getImageUrl(),mCurrentAnimator,img_preview,zoomContainer
        ,mShortAnimationDuration
        );
    }


    private boolean hasPermission(String permission){
        return ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d("perm", "done" + "");

                } else {

                }
                return;
            }
        }
    }

}
