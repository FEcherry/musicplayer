package com.example.fe.musicplayer;

import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private ImageButton mBefore,mStart,mNext,mTxt;
    private  boolean bisReleased=false;
    public MediaPlayer mPlayer=new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBefore=(ImageButton) findViewById((R.id.before));
        mStart=(ImageButton) findViewById((R.id.start));
        mNext=(ImageButton) findViewById((R.id.next));
        mTxt=(ImageButton) findViewById((R.id.txt));

        mBefore.setOnClickListener(new ImageButton.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                try {
                    if(mPlayer.isPlaying()==true){
                        mPlayer.reset();
                    }
                    mPlayer.setDataSource("/storage/emulated/0/kgmusic/download/邓紫棋 - All About U.mp3");
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        mStart.setOnClickListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    if(mPlayer.isPlaying()==true){
                        mPlayer.pause();
                    }
                    mPlayer.setDataSource("/storage/emulated/0/kgmusic/download/邓紫棋 - 偶尔.mp3");
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mNext.setOnClickListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    if(mPlayer.isPlaying()==true){
                        mPlayer.reset();
                    }
                    mPlayer.setDataSource("/storage/emulated/0/kgmusic/download/邓紫棋 - 是否.mp3");
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
