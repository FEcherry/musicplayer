package com.example.fe.musicplayer;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony.Mms.Part;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.view.ViewGroup;

public class PlayerFragment extends Fragment implements View.OnClickListener,
        OnCompletionListener, OnPreparedListener {

    private NetWorkAudioPlayer netWorkAudioPlayer;

    private ImageButton mTxtBtn;
    private ImageButton mTxtBtn2;
    private ImageButton mPlayBtn;
    private ImageButton mPreBtn;
    private ImageButton mNextBtn;

    private SeekBar mSeekBar;
    private TextView mContent;
    private TextView mStartTextView;
    private TextView mEndTextView;
    boolean isPause = true;
    private Intent intent;
    private View view;
    private static int totalTime;
    private int finalTotalTime;
    private int startTime, endTime;
    private static int part = 0;
    private ClassificationFragment classification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.player, null);
            netWorkAudioPlayer = new NetWorkAudioPlayer(getActivity());
            classification = new ClassificationFragment();
            initView();
            setListener();
        }

        UpdateSeekBarThread thread = new UpdateSeekBarThread();
        thread.start();

        return view;
    }


    public void setData(Bundle bundle) {
        part = bundle.getInt("part");
    }

    public int getData() {
        part = getArguments().getInt("part");
        return part;
    }



    public class UpdateSeekBarThread extends Thread {

        public void run() {
            while (netWorkAudioPlayer != null) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                switch (part) {
                    case 0:
                        handler.sendEmptyMessage(0);
                        break;
                    case 1:
                        handler.sendEmptyMessage(1);
                        break;
                    case 2:
                        handler.sendEmptyMessage(2);
                        break;
                    case 3:
                        handler.sendEmptyMessage(3);
                        break;
                }

            }
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (netWorkAudioPlayer != null
                            && netWorkAudioPlayer.isPlaying()) {
                        totalTime = netWorkAudioPlayer.getDuration();
                        int currentTime = netWorkAudioPlayer.getCurrentPosition();
                        mEndTextView.setText(getTimeText(totalTime));
                        int seekBarMax = mSeekBar.getMax();
                        if (totalTime > 0 && currentTime > 0 && seekBarMax > 0) {
                            mStartTextView.setText(getTimeText(currentTime));
                            mSeekBar.setProgress((int) (seekBarMax
                                    * (float) currentTime / totalTime));
                        }
                    }
                    break;
                case 1:
                    timeArguments();
                    break;
                case 2:
                    timeArguments();
                    break;
                case 3:
                    timeArguments();
                    break;
            }
        }

        private void timeArguments() {
            if (netWorkAudioPlayer != null && netWorkAudioPlayer.isPlaying()) {
                totalTime = netWorkAudioPlayer.getDuration();
                classification.setTotalTime(totalTime);
                startTime = getArguments().getInt("startTime");
                endTime = getArguments().getInt("endTime");
                finalTotalTime = endTime - startTime;
                int currentTime = netWorkAudioPlayer.getCurrentPosition();
                mEndTextView.setText(getTimeText(endTime));
                int seekBarMax = mSeekBar.getMax();
                if (finalTotalTime > 0 && currentTime > 0 && seekBarMax > 0) {
                    mStartTextView.setText(getTimeText(currentTime));
                    mSeekBar.setProgress((int) (seekBarMax * (float) currentTime / finalTotalTime));
                }
            }
        }

        private String getTimeText(int time) {
            // TODO Auto-generated method stub
            int totalSeconds = time / 1000;
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            String showTime;
            if (seconds > 9 && seconds < 60) {
                showTime = minutes + ":" + seconds;
            } else {
                showTime = minutes + ":0" + seconds;

            }
            return showTime;
        }

    };

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        netWorkAudioPlayer.release();
        netWorkAudioPlayer = null;
        if (intent != null) {
            getActivity().stopService(intent);
        }
        super.onDestroy();
    }

    public void initView() {
        mPlayBtn = (ImageButton) view.findViewById(R.id.playButton);
        mPreBtn = (ImageButton) view.findViewById(R.id.preButton);
        mNextBtn = (ImageButton) view.findViewById(R.id.nextButton);
        mTxtBtn = (ImageButton) view.findViewById(R.id.txt_hide);
        mTxtBtn2 = (ImageButton) view.findViewById(R.id.txt_show);
        mContent = (TextView) view.findViewById(R.id.audioTxt);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
        mStartTextView = (TextView) view.findViewById(R.id.startTextView);
        mEndTextView = (TextView) view.findViewById(R.id.endTextView);
    }

    public void setListener() {
        mPlayBtn.setOnClickListener(this);
        mPreBtn.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
        mTxtBtn.setOnClickListener(this);
        mTxtBtn2.setOnClickListener(this);
        netWorkAudioPlayer.setOnPreparedListener(this);
        netWorkAudioPlayer.setOnCompletionListener(this);
        mSeekBar.setOnSeekBarChangeListener(new ProgressBarListener());

    }

    @Override
    public void onPrepared(MediaPlayer arg0) {
        // TODO Auto-generated method stub
        netWorkAudioPlayer.start();
        netWorkAudioPlayer.setPaused(false);
        netWorkAudioPlayer.setPrepared(true);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        // TODO Auto-generated method stub

    }

    private class ProgressBarListener implements OnSeekBarChangeListener {
        private int part;


        public ProgressBarListener() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // TODO Auto-generated method stub
            if (fromUser) {
                netWorkAudioPlayer.seekTo((int) (netWorkAudioPlayer
                        .getDuration() * (float) progress / seekBar.getMax()));
                seekBar.setProgress(progress);
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStopTrackingTouch(SeekBar arg0) {
            // TODO Auto-generated method stub

        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.playButton:
                if (isPause) {
                    netWorkAudioPlayer.play();
                    mPlayBtn.setBackgroundResource(R.drawable.pause);
                    isPause = false;
                } else {
                    netWorkAudioPlayer.playPause();
                    mPlayBtn.setBackgroundResource(R.drawable.play);
                    isPause = true;
                }
                break;

            case R.id.preButton:
                netWorkAudioPlayer.playPrevious();
                break;

            case R.id.nextButton:
                netWorkAudioPlayer.playNext();
                break;

            case R.id.txt_hide:
                intent=getActivity().getIntent();
                mContent.setText(intent.getStringExtra("content"));
                mTxtBtn.setVisibility(View.GONE);
                mTxtBtn2.setVisibility(View.VISIBLE);

            case R.id.txt_show:
                mContent.setText("");
                mTxtBtn2.setVisibility(View.GONE);
                mTxtBtn.setVisibility(View.VISIBLE);

        }
        intent = new Intent(getActivity(), Myservice.class);
        intent.putExtra("songName", "This is networkPlayer's song.");
        getActivity().startService(intent);
    }

}
