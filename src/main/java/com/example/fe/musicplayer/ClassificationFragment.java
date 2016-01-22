package com.example.fe.musicplayer;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.StaticLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class ClassificationFragment extends Fragment implements OnClickListener {

    private ImageButton mPart1Btn;
    private ImageButton mStar1Btn;
    private ImageButton mPart2Btn;
    private ImageButton mStar2Btn;
    private ImageButton mPart3Btn;
    private ImageButton mStar3Btn;
    private View view;
    private boolean isLight = false;
    private ViewPager mViewPager;
    private NetWorkAudioPlayer netWorkAudioPlayer;
    private Bundle bundle;
    private ArgumentPassedListener argumentPassedListener;
    private int totalTime;

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public interface ArgumentPassedListener {
        public void onArgumentPassed(Bundle bundle);
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        argumentPassedListener = (ArgumentPassedListener) activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.classification, null);
        netWorkAudioPlayer = new NetWorkAudioPlayer(getActivity());
        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        // TODO Auto-generated method stub
        mPart1Btn.setOnClickListener(this);
        mPart2Btn.setOnClickListener(this);
        mPart3Btn.setOnClickListener(this);
        mStar1Btn.setOnClickListener(this);
        mStar2Btn.setOnClickListener(this);
        mStar3Btn.setOnClickListener(this);

    }

    private void initView() {
        // TODO Auto-generated method stub
        mPart1Btn = (ImageButton) view.findViewById(R.id.part1);
        mPart2Btn = (ImageButton) view.findViewById(R.id.part2);
        mPart3Btn = (ImageButton) view.findViewById(R.id.part3);

        mStar1Btn = (ImageButton) view.findViewById(R.id.star1);
        mStar2Btn = (ImageButton) view.findViewById(R.id.star2);
        mStar3Btn = (ImageButton) view.findViewById(R.id.star3);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        ClassificationFragment classification = new ClassificationFragment();
        bundle = new Bundle();

        int totalTime = getTotalTime();
        switch (v.getId()) {
            case R.id.part1:
                bundle.putInt("part", 1);
                bundle.putInt("startTime", 0);
                bundle.putInt("endTime", totalTime / 3);
                argumentPassedListener.onArgumentPassed(bundle);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.part2:
                bundle.putInt("part", 2);
                bundle.putInt("startTime", totalTime / 3);
                bundle.putInt("endTime", totalTime * 2 / 3);
                mViewPager.setCurrentItem(3);
                break;
            case R.id.part3:
                bundle.putInt("part", 3);
                bundle.putInt("startTime", totalTime * 2 / 3);
                bundle.putInt("endTime", totalTime);
                mViewPager.setCurrentItem(4);
                break;
            case R.id.star1:
                if (isLight) {
                    mStar1Btn.setImageResource(R.drawable.starempty);
                    isLight = false;
                } else {
                    mStar1Btn.setImageResource(R.drawable.starsolid);
                    isLight = true;
                }
                break;
            case R.id.star2:
                if (isLight) {
                    mStar2Btn.setImageResource(R.drawable.starempty);
                    isLight = false;
                } else {
                    mStar2Btn.setImageResource(R.drawable.starsolid);
                    isLight = true;
                }
                break;
            case R.id.star3:
                if (isLight) {
                    mStar3Btn.setImageResource(R.drawable.starempty);
                    isLight = false;
                } else {
                    mStar3Btn.setImageResource(R.drawable.starsolid);
                    isLight = true;
                }
                break;

        }

    }
}
