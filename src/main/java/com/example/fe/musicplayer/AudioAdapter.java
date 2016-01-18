package com.example.fe.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fe on 16-1-12.
 */
public class AudioAdapter extends BaseAdapter {

    private List<AudioBean> mList;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
//    private int mStart,mEnd;
//    public static String[] URLS;   //存放图片url地址
//    private boolean mFirstIn;

    private View header;//顶部布局文件

    public AudioAdapter(Context context,List<AudioBean> data,RefreshListView listView){
        mList=data;
        mInflater=LayoutInflater.from(context);
        //初始化ImageLoader,保留唯一的LRUCache
        mImageLoader=new ImageLoader(listView);
//        URLS=new String[data.size()];
//        for(int i=0;i<data.size();i++){
//            URLS[i]=data.get(i).audioIconUrl;
//        }
//        mFirstIn=true;
        //注册对应事件
//        listView.setOnScrollListener(this);


    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder =new ViewHolder();
            convertView=mInflater.inflate(R.layout.list_item,null);
            viewHolder.mIcon=(ImageView)convertView.findViewById(R.id.imageView);
            viewHolder.mTitle=(TextView)convertView.findViewById(R.id.titleTextView);
            viewHolder.mDate=(TextView)convertView.findViewById(R.id.dateTextView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.mIcon.setImageResource(R.drawable.launcher);
        String url=mList.get(position).audioIconUrl;
        viewHolder.mIcon.setTag(url);
        mImageLoader.showImageByAsyncTask(viewHolder.mIcon, url);
        viewHolder.mTitle.setText(mList.get(position).audioTitle);
        viewHolder.mDate.setText(mList.get(position).audioContent);
        return convertView;

    }

//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if(scrollState==SCROLL_STATE_IDLE){
//            //加载可见项
//            mImageLoader.loadImages(mStart,mEnd);
//        }else{
//            //停止任务
//            mImageLoader.cancelAllTasks();
//        }
//    }

//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        mStart=firstVisibleItem;
//        mEnd=firstVisibleItem+visibleItemCount;
//        //第一次显示的时候调用
//        if(mFirstIn&&visibleItemCount>0){
//            mImageLoader.loadImages(mStart,mEnd);
//            mFirstIn=false;
//        }
//    }

    class ViewHolder{
        public TextView mTitle,mDate,mTime;
        public ImageView mIcon;
    }


}
