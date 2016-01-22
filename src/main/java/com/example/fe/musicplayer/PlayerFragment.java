package com.example.fe.musicplayer;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.renderscript.RenderScript;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.view.ViewGroup;

public class PlayerFragment extends Fragment implements View.OnClickListener,
        OnCompletionListener, OnPreparedListener {

    private NetWorkAudioPlayer netWorkAudioPlayer;

    private ImageView mBackgroundImageView;
    private ImageButton mTxtBtn;
    private ImageButton mPlayBtn;
    private ImageButton mPreBtn;
    private ImageButton mNextBtn;

    private SeekBar mSeekBar;
    private TextView mContent;
    private TextView mStartTextView;
    private TextView mEndTextView;
    private boolean isPause = true;
    private boolean isTxtShow=false;
    private Intent intent;
    private View view;
    private static int totalTime;
    private int finalTotalTime;
    private int startTime, endTime;
    private static int part = 0;
    private ClassificationFragment classification;
    private ImageLoader mImageLoader;
    private ViewPager mViewPager;

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

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
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
        mBackgroundImageView=(ImageView)view.findViewById(R.id.backgroundImage);
        mPlayBtn = (ImageButton) view.findViewById(R.id.playButton);
        mPreBtn = (ImageButton) view.findViewById(R.id.preButton);
        mNextBtn = (ImageButton) view.findViewById(R.id.nextButton);
        mTxtBtn = (ImageButton) view.findViewById(R.id.txt_hide);
        mContent = (TextView) view.findViewById(R.id.audioTxt);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
        mStartTextView = (TextView) view.findViewById(R.id.startTextView);
        mEndTextView = (TextView) view.findViewById(R.id.endTextView);
        //设置传入的背景图
        intent=getActivity().getIntent();
        mImageLoader=new ImageLoader();
        String iconUrl = intent.getStringExtra("iconUrl");
        mImageLoader.showImageByAsyncTask(mBackgroundImageView, iconUrl);
    }

    public void setListener() {
        mPlayBtn.setOnClickListener(this);
        mPreBtn.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
        mTxtBtn.setOnClickListener(this);
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
                if(!isTxtShow) {
                    intent = getActivity().getIntent();
                    mContent.setText(intent.getStringExtra("content"));
                    mTxtBtn.setImageResource(R.drawable.txt_show);
                    isTxtShow=true;
                }else{
                    mContent.setText("");
                    mTxtBtn.setImageResource(R.drawable.txt_hide);
                    isTxtShow=false;
                }

        }
        intent = new Intent(getActivity(), Myservice.class);
        intent.putExtra("songName", "This is networkPlayer's song.");
        getActivity().startService(intent);
    }


    /**
     * 高斯模糊
     * @param bmp
     * @return
     */
//    public static Bitmap convertToBlur(Bitmap bmp) {
//        // 高斯矩阵
//        int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };
//
//        int width = bmp.getWidth();
//        int height = bmp.getHeight();
//        Bitmap newBmp = Bitmap.createBitmap(width, height,
//                Bitmap.Config.RGB_565);
//
//        int pixR = 0;
//        int pixG = 0;
//        int pixB = 0;
//
//        int pixColor = 0;
//
//        int newR = 0;
//        int newG = 0;
//        int newB = 0;
//
//        int delta = 16; // 值越小图片会越亮，越大则越暗
//
//        int idx = 0;
//        int[] pixels = new int[width * height];
//        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
//        for (int i = 1, length = height - 1; i < length; i++) {
//            for (int k = 1, len = width - 1; k < len; k++) {
//                idx = 0;
//                for (int m = -1; m <= 1; m++) {
//                    for (int n = -1; n <= 1; n++) {
//                        pixColor = pixels[(i + m) * width + k + n];
//                        pixR = Color.red(pixColor);
//                        pixG = Color.green(pixColor);
//                        pixB = Color.blue(pixColor);
//
//                        newR = newR + pixR * gauss[idx];
//                        newG = newG + pixG * gauss[idx];
//                        newB = newB + pixB * gauss[idx];
//                        idx++;
//                    }
//                }
//
//                newR /= delta;
//                newG /= delta;
//                newB /= delta;
//
//                newR = Math.min(255, Math.max(0, newR));
//                newG = Math.min(255, Math.max(0, newG));
//                newB = Math.min(255, Math.max(0, newB));
//
//                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
//
//                newR = 0;
//                newG = 0;
//                newB = 0;
//            }
//        }
//
//        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
//
//        return newBmp;
//    }
//
//    //drawable转bitmap
//    public Bitmap drawableToBitmap(Drawable drawable) {
//        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight(),
//                drawable.getOpacity() != PixelFormat.OPAQUE ?
//                        Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
//        Canvas canvas = new Canvas(bitmap);
//        //canvas.setBitmap(bitmap);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        drawable.draw(canvas);
//        return bitmap;
//    }

}
