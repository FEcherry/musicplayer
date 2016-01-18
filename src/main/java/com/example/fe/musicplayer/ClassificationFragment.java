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

    private ImageButton part1;
    private ImageButton star1;
    private ImageButton part2;
    private ImageButton star2;
    private ImageButton part3;
    private ImageButton star3;
    private View view;
    boolean light = false;
    private ViewPager viewPager;
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

    // ��fragment�ڲ�����һ���ص��ӿڣ���Activityʵ������ӿڣ�����fragmengt���øûص������������ݴ��ݸ�Activity
    public interface ArgumentPassedListener {
        public void onArgumentPassed(Bundle bundle);
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
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
        part1.setOnClickListener(this);
        part2.setOnClickListener(this);
        part3.setOnClickListener(this);
        star1.setOnClickListener(this);
        star2.setOnClickListener(this);
        star3.setOnClickListener(this);

    }

    private void initView() {
        // TODO Auto-generated method stub
        part1 = (ImageButton) view.findViewById(R.id.part1);
        part2 = (ImageButton) view.findViewById(R.id.part2);
        part3 = (ImageButton) view.findViewById(R.id.part3);

        star1 = (ImageButton) view.findViewById(R.id.star1);
        star2 = (ImageButton) view.findViewById(R.id.star2);
        star3 = (ImageButton) view.findViewById(R.id.star3);

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
                viewPager.setCurrentItem(1);
                break;
            case R.id.part2:
                bundle.putInt("part", 2);
                bundle.putInt("startTime", totalTime / 3);
                bundle.putInt("endTime", totalTime * 2 / 3);
                viewPager.setCurrentItem(1);
                break;
            case R.id.part3:
                bundle.putInt("part", 3);
                bundle.putInt("startTime", totalTime * 2 / 3);
                bundle.putInt("endTime", totalTime);
                viewPager.setCurrentItem(1);
                break;
            case R.id.star1:
                if (light) {
                    star1.setImageResource(R.drawable.starempty);
                    light = false;
                } else {
                    star1.setImageResource(R.drawable.starsolid);
                    light = true;
                }
                break;
            case R.id.star2:
                if (light) {
                    star2.setImageResource(R.drawable.starempty);
                    light = false;
                } else {
                    star2.setImageResource(R.drawable.starsolid);
                    light = true;
                }
                break;
            case R.id.star3:
                if (light) {
                    star3.setImageResource(R.drawable.starempty);
                    light = false;
                } else {
                    star3.setImageResource(R.drawable.starsolid);
                    light = true;
                }
                break;

        }

//		Toast.makeText(getActivity(), getArguments().getInt("part"),
//				Toast.LENGTH_SHORT).show();
        // Log.i("------part", getArguments().getInt("part")+"");
        // Log.i("------part", getArguments().getInt("endTime")+"");
    }
}
