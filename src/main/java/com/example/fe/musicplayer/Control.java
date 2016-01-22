package com.example.fe.musicplayer;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Control extends FragmentActivity implements OnClickListener,
        ClassificationFragment.ArgumentPassedListener {
    private TextView mTitle;
    private ViewPager mViewPager;
    private List<Fragment> mListFragment;
    private FragmentPagerAdapter mAdapter;
    private ImageButton mBack;
    private Bundle bundle;
    ClassificationFragment classificationFragment;
    PlayerFragment networkPlayerFragment;
    Exercise1Fragment exercise1Fragment;
    Exercise2Fragment exercise2Fragment;
    Exercise3Fragment exercise3Fragment;

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.control);
        initView();
    }

    private void initView() {
        // TODO Auto-generated method stub
        Intent intent=getIntent();
        mTitle=(TextView)this.findViewById(R.id.audioTitle);
        mTitle.setText(intent.getStringExtra("title"));

        mBack = (ImageButton) this.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mViewPager = (ViewPager) this.findViewById(R.id.viewPager);
        mListFragment = new ArrayList<Fragment>();

        classificationFragment = new ClassificationFragment();
        exercise1Fragment = new Exercise1Fragment();
        networkPlayerFragment = new PlayerFragment();

        classificationFragment.setViewPager(mViewPager);
        networkPlayerFragment.setViewPager(mViewPager);
        exercise1Fragment.setViewPager(mViewPager);
        exercise2Fragment.setViewPager(mViewPager);
        exercise3Fragment.setViewPager(mViewPager);
        mListFragment.add(classificationFragment);
        mListFragment.add(networkPlayerFragment);
        mListFragment.add(exercise1Fragment);
        mListFragment.add(exercise2Fragment);
        mListFragment.add(exercise3Fragment);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return mListFragment.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                // TODO Auto-generated method stub
                return mListFragment.get(arg0);
            }
        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent(Control.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onArgumentPassed(Bundle bundle) {
        if (!networkPlayerFragment.isAdded()) {
            networkPlayerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).add(networkPlayerFragment, "fragment").commit();

        }
    }
}

