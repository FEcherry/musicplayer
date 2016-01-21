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

    public AudioAdapter(Context context,List<AudioBean> data,RefreshListView listView){
        mList=data;
        mInflater=LayoutInflater.from(context);
        //初始化ImageLoader,保留唯一的LRUCache
        mImageLoader=new ImageLoader(listView);


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



    class ViewHolder{
        public TextView mTitle,mDate,mTime,mContent;
        public ImageView mIcon;
    }


}
