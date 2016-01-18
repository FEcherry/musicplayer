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
import android.widget.Toast;

public class Control extends FragmentActivity implements OnClickListener,
        ClassificationFragment.ArgumentPassedListener {
    private String TAG = "MainActivity";
    private ViewPager viewPager;
    private List<Fragment> listFragment;
    private FragmentPagerAdapter adapter;
    private ImageButton mBack;
    private Bundle bundle;
    ClassificationFragment classificationFragment;
    PlayerFragment networkPlayerFragment;
    Exercise1Fragment exercise1Fragment;

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
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        mBack = (ImageButton) this.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        viewPager = (ViewPager) this.findViewById(R.id.viewPager);
        listFragment = new ArrayList<Fragment>();

        classificationFragment = new ClassificationFragment();
        exercise1Fragment = new Exercise1Fragment();
        networkPlayerFragment = new PlayerFragment();

        classificationFragment.setViewPager(viewPager);
        listFragment.add(classificationFragment);
        listFragment.add(networkPlayerFragment);
        listFragment.add(exercise1Fragment);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return listFragment.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                // TODO Auto-generated method stub
                return listFragment.get(arg0);
            }
        };

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent(Control.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    // ��networkPlayer���ܵ�Ҫ���ݵĲ���
    public void onArgumentPassed(Bundle bundle) {
        // networkPlayerFragment.setData(bundle);

        if (!networkPlayerFragment.isAdded()) {
            networkPlayerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null).add(networkPlayerFragment, "fragment").commit();

        }
    }
}

