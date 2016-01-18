package com.example.fe.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

/**
 * Created by fe on 16-1-18.
 */
public class MainActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        };
        timer.schedule(task,SPLASH_DISPLAY_LENGHT);
    }
}
