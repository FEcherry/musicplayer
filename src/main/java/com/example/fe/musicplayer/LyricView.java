package com.example.fe.musicplayer;

import android.content.Context;
import android.graphics.Paint;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by fe on 16-1-18.
 */
public class LyricView extends TextView {

    private Paint mPaint;
    private float mX;
    private static Lyric mLyric;

    public LyricView(Context context) {
        super(context);
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public LyricView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(){
        setFocusable(true);
        MediaStore.Audio.PlaylistsColumns pli=new MediaStore.Audio.PlaylistsColumns() {
        }
    }
}


