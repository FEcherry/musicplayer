package com.example.fe.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fe on 16-1-12.
 */
public class AudioAdapter extends BaseAdapter {

    private List<AudioBean> mList;
    private LayoutInflater mInflater;

    public AudioAdapter(Context context,List<AudioBean> data){
        mList=data;
        mInflater=LayoutInflater.from(context);
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
            viewHolder.ivIcon=(ImageView)convertView.findViewById(R.id.imageView);
            viewHolder.tvTitle=(TextView)convertView.findViewById(R.id.titleTextView);
            viewHolder.tvContent=(TextView)convertView.findViewById(R.id.dateTextView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.drawable.launcher);
        viewHolder.tvTitle.setText(mList.get(position).audioTitle);
        viewHolder.tvContent.setText(mList.get(position).audioContent);
        return convertView;

    }

    class ViewHolder{
        public TextView tvTitle,tvContent;
        public ImageView ivIcon;
    }
}
