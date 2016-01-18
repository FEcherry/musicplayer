package com.example.fe.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class Exercise3Fragment extends Activity{
    private ImageButton mBack;
    private ImageButton mHorn;
    private ImageButton mStar;
    private ImageButton mLast;
    private TextView mTxt;
    private TextView mAnswer;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_three);
        initView();
    }

    private void initView(){
        mBack=(ImageButton)view.findViewById(R.id.back);
        mHorn=(ImageButton)view.findViewById(R.id.ib_horn);
        mStar=(ImageButton)view.findViewById(R.id.ib_starEmpty);
        mTxt=(TextView)view.findViewById(R.id.tv_txt);
        mAnswer=(TextView)view.findViewById(R.id.tv_answer);
        mLast=(ImageButton)view.findViewById(R.id.ib_next);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Exercise3Fragment.this,HomeActivity.class);
                startActivity(intent);

            }
        });
        mLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Exercise3Fragment.this,Exercise2Fragment.class);
                startActivity(intent);

            }
        });
    }
}
