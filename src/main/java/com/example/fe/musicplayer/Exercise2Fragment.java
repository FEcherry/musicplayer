package com.example.fe.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class Exercise2Fragment extends Fragment{
    private ImageButton mBack;
    private ImageButton mHorn;
    private ImageButton mStar;
    private ImageButton mLast;
    private ImageButton mNext;
    private TextView mTxt;
    private TextView mAnswer;
    private boolean isLight=false;
    View view;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view=inflater.inflate(R.layout.exercise_two, null);
        initView();
        return view;

    }

    private void initView(){
        mBack=(ImageButton)view.findViewById(R.id.back);
        mHorn=(ImageButton)view.findViewById(R.id.ib_horn);
        mStar=(ImageButton)view.findViewById(R.id.ib_starEmpty);
        mTxt=(TextView)view.findViewById(R.id.tv_txt);
        mAnswer=(TextView)view.findViewById(R.id.tv_answer);
        mLast=(ImageButton)view.findViewById(R.id.ib_last);
        mNext=(ImageButton)view.findViewById(R.id.ib_next);


        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),HomeActivity.class);
                startActivity(intent);

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

        mLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(4);

            }
        });
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }
}