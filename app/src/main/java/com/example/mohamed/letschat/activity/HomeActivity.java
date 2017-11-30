package com.example.mohamed.letschat.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
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
import com.example.mohamed.letschat.view.HomeView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeView,View.OnClickListener {
    private static final String USER ="USER" ;
    private static final int IMG = 0;
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
        dataManger=((MyApp) getApplication()).getDataManger();
        mUser=dataManger.getUser();
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
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Choose Your photo"),IMG);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMG && resultCode==RESULT_OK){
            presenter.edtIMG(data.getData(), mProfileImge, new HomePresenter.update() {
                @Override
                public void sucess(User user) {
                    mUser=user;
                }
            });
        }
    }

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
        zoomImageFromThumb(mProfileImge,mUser.getImageUrl());
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }

    private void zoomImageFromThumb(final View thumbView, String imageResId){
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }
        Glide.with(this).load(Uri.parse(imageResId))
                .error(R.drawable.logo)
                .into(img_preview);
        // zoomIMG.setImageResource(imageResId);
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();
        thumbView.getGlobalVisibleRect(startBounds);
        zoomContainer
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }


        thumbView.setAlpha(0f);
        img_preview.setVisibility(View.VISIBLE);

        img_preview.setPivotX(0f);
        img_preview.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(img_preview, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(img_preview, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(img_preview, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(img_preview,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        final float startScaleFinal = startScale;

        img_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(img_preview, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(img_preview,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(img_preview,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(img_preview,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        img_preview.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        img_preview.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });

    }
}
