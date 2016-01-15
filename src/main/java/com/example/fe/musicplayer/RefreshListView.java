package com.example.fe.musicplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by fe on 16-1-13.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener{

    View header;//顶部布局文件
    int headerHeight;//顶部布局文件的高度
    int firstVisibleItem;//当前第一个可见的Item的位置
    int scrollState;//当前滚动状态
    boolean isRemark;//标记，当前是在listview最顶端摁下的
    int startY;//摁下的Ｙ值
    int state;//当前的状态
    final int NONE=0;//正常状态
    final int PULL=1;//提示下拉状态
    final int RELEASE=2;//提示释放状态
    final int REFRESHING=3;//刷新状态
    IRefreshListener iRefreshListener;//刷新数据的接口

    View footer;//底部布局文件
    int totalItemCount;//Item总数量
    int lastVisibleItem;//最后一个可见Item
    boolean isLoading;//正在加载
    ILoadListener iLoadListener;//加载更多数据的接口



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
        topPadding(-headerHeight);
        this.addHeaderView(header);

        LayoutInflater inflater1=LayoutInflater.from(context);
        footer=inflater1.inflate(R.layout.footer,null);
        footer.setVisibility(View.GONE);
        this.addFooterView(footer);
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
        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState=scrollState;

        if(totalItemCount==lastVisibleItem && scrollState==SCROLL_STATE_IDLE){
            //加载更多界面
            if(!isLoading){
                isLoading=true;
                footer.setVisibility(View.VISIBLE);
                iLoadListener.onLoad();
            }

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem=firstVisibleItem;
        this.lastVisibleItem=firstVisibleItem+visibleItemCount;
        this.totalItemCount=totalItemCount;
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
                if(state==RELEASE){
                    state=REFRESHING;
                    //加载最新数据
                    refreshViewByState();
                    iRefreshListener.onRefresh();
                }else if(state==PULL){
                    state=NONE;
                    isRemark=false;
                    refreshViewByState();
                }
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
        int topPadding=space-headerHeight;
        switch (state){
            case NONE:
                if(space>0){
                    state=PULL;
                    refreshViewByState();
                }
                break;

            case PULL:
                topPadding(topPadding);
                if(space>headerHeight+30 && scrollState==SCROLL_STATE_TOUCH_SCROLL){
                    state=RELEASE;
                    refreshViewByState();
                }
                break;

            case RELEASE:
                topPadding(topPadding);
                if(space<headerHeight+30 ){
                    state=PULL;
                    refreshViewByState();
                }else if(space<=0){
                    state=NONE;
                    isRemark=false;
                    refreshViewByState();
                }
                break;
        }
    }

    //根据当前状态，改变界面显示
    private void refreshViewByState(){
        TextView tip= (TextView) header.findViewById(R.id.tip);
        ImageView arrowDown= (ImageView) header.findViewById(R.id.arrowDown);
        ImageView arrowUp= (ImageView) header.findViewById(R.id.arrowUp);
        ProgressBar progress= (ProgressBar) header.findViewById(R.id.progress);
//        RotateAnimation anim=new RotateAnimation(0,180,
//                RotateAnimation.RELATIVE_TO_SELF,0.5f,
//                RotateAnimation.RELATIVE_TO_SELF,0.5f);
//        anim.setDuration(500);
//        anim.setFillAfter(true);
//        RotateAnimation anim1=new RotateAnimation(180,0,
//                RotateAnimation.RELATIVE_TO_SELF,0.5f,
//                RotateAnimation.RELATIVE_TO_SELF,0.5f);
//        anim1.setDuration(500);
//        anim1.setFillAfter(true);

        switch (state) {
            case NONE:
//                arrow.clearAnimation();
                topPadding((-headerHeight));
                break;

            case PULL:
                arrowDown.setVisibility(View.VISIBLE);
                arrowUp.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                tip.setText("Pull Down");
//                arrow.clearAnimation();
//                arrow.setAnimation(anim1);
                break;

            case RELEASE:
                arrowDown.setVisibility(View.GONE);
                arrowUp.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("Release");
//                arrow.clearAnimation();
//                arrow.setAnimation(anim);
                break;

            case REFRESHING:
                topPadding(10);
                arrowDown.setVisibility(View.GONE);
                arrowUp.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText("Loading……");
                break;
        }
    }

    public void setRefreshListenerInterface(IRefreshListener iRefreshListener){
        this.iRefreshListener=iRefreshListener;
    }

    public void setLoadListenerInterface(ILoadListener iLoadListener){
        this.iLoadListener=iLoadListener;
    }

    //获取完数据
    public void refreshComplete(){
        state=NONE;
        isRemark=false;
        refreshViewByState();
//        TextView lastUpdateTime= (TextView) header.findViewById(R.id.lastupdatetime);
//        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss");
//        Date date=new Date(System.currentTimeMillis());
//        String time=format.format(date);
//        lastUpdateTime.setText(time);
    }

    //加载完毕
    public void loadComplete(){
        isLoading=false;
        footer.setVisibility(View.GONE);
    }

    public interface  IRefreshListener{
        public void onRefresh();
    }

    //加载更多数据的回调接口
    public interface ILoadListener{
        public void onLoad();
    }
}
