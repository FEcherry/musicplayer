package com.example.fe.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.security.PrivateKey;


public class Exercise1Fragment extends Fragment{
    private ImageButton mHorn;
    private ImageButton mStar;
    private ImageButton mNext;
    private TextView mTxt;
    private TextView mAnswer;
    private Boolean isLight=false;
    View view;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view=inflater.inflate(R.layout.exercise, null);
        initView();
        return view;

    }

    private void initView(){
        mHorn=(ImageButton)view.findViewById(R.id.ib_horn);
        mStar=(ImageButton)view.findViewById(R.id.ib_starEmpty);
        mTxt=(TextView)view.findViewById(R.id.tv_txt);
        mAnswer=(TextView)view.findViewById(R.id.tv_answer);
        mNext=(ImageButton)view.findViewById(R.id.ib_next);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(3);

            }
        });
        mStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!isLight){
                   mStar.setImageResource(R.drawable.starsolid);
                   isLight=true;
               }else{
                   mStar.setImageResource(R.drawable.starempty);
               }
            }
        });
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }
}
