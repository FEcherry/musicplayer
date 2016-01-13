package com.example.fe.musicplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;


/**
 * Created by fe on 16-1-13.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {

    View header;//顶部布局文件
    int headerHeight;//顶部布局文件的高度
    int firstVisibleItem;//当前第一个可见的Item的位置
    boolean isRemark;//标记，当前是在listview最顶端摁下的
    int startY;//摁下的Ｙ值

    int state;//当前的状态
    final int NONE=0;//正常状态
    final int PULL=1;//提示下拉状态
    final int RELEASE=2;//提示释放状态
    final int REFLASHING=3;//刷新状态


    public RefreshListView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    //初始化界面，添加顶部布局文件到listview
    private void initView(Context context){
        LayoutInflater inflater=LayoutInflater.from(context);
        header=inflater.inflate(R.layout.header,null);
        measureView(header);
        headerHeight=header.getMeasuredHeight();
        Log.i("tag","headerHeight="+headerHeight);
        topPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);
    }

    //通知父布局，占用的宽、高
    private void measureView(View view){
        ViewGroup.LayoutParams p=view.getLayoutParams();
        if(p==null){
            p=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width=ViewGroup.getChildMeasureSpec(0,0,p.width);
        int height;
        int tempHeight=p.height;
        if(tempHeight>0){
            height=MeasureSpec.makeMeasureSpec(tempHeight,MeasureSpec.EXACTLY);
        }else{
            height=MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }

    //设置header布局上边距
    private void topPadding(int topPadding){
        header.setPadding(header.getPaddingLeft(),topPadding,header.getPaddingRight(),header.getPaddingBottom());
        header.invalidate();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem=firstVisibleItem;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(firstVisibleItem==0){
                    isRemark=true;
                    startY=(int)ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.onTouchEvent(ev);
    }

    //判断移动过程操作
    private void onMove(MotionEvent ev){
        if(!isRemark){
            return;
        }
        int tempY=(int)ev.getY();
        int space=tempY-startY;
        switch (state){
            case NONE:
                break;
            case PULL:
                break;
            case RELEASE:
                break;
            case NONE:
                break;
        }

    }
}
