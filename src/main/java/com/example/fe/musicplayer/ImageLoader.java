package com.example.fe.musicplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by fe on 16-1-13.
 */
public class ImageLoader {

    private ImageView mImageView;
    private String mUrl;
    //创建Cache
    private LruCache<String,Bitmap> mCaches;
//    private RefreshListView mListView;
//    private Set<AudioAsyncTask> mTask;

    public ImageLoader(RefreshListView listView){
//        mListView=listView;
//        mTask=new HashSet<>();
        //获取最大可用内存
        int maxMemory=(int) Runtime.getRuntime().maxMemory();
        int cacheSize=maxMemory/4;
        mCaches=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存的时候调用
                return value.getByteCount();
            }
        };
    }

    //增加到缓存
    public void addBitmapToCache(String url,Bitmap bitmap){
        if(getBitmapFromCache(url)==null){
            mCaches.put(url,bitmap);
        }
    }

    //从缓存中获取数据
    public Bitmap getBitmapFromCache(String url){
        return mCaches.get(url);


    }

    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mImageView.getTag().equals(mUrl))
                mImageView.setImageBitmap((Bitmap)msg.obj);
        }
    };

    public void showImageByThread(ImageView imageView, final String url){

        mImageView=imageView;
        mUrl=url;

        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap bitmap=getBitmapFromURL(url);
                Message message=Message.obtain();
                message.obj=bitmap;
                mhandler.sendMessage(message);
            }
        }.start();
    }

    public Bitmap getBitmapFromURL(String urlString){
        Bitmap bitmap;
        InputStream is=null;
        try {
            URL url=new URL(urlString);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            is=new BufferedInputStream(connection.getInputStream());
            bitmap= BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void showImageByAsyncTask(ImageView imageView,String url){
        //从缓存中取出对应的图片
        Bitmap bitmap=getBitmapFromCache(url);
        //如果缓存中没有，那么去下载
        if(bitmap==null) {
            new AudioAsyncTask(imageView,url).execute(url);
        }else{
            imageView.setImageBitmap(bitmap);
        }
    }

//    public void cancelAllTasks() {
//        if(mTask!=null){
//            for(AudioAsyncTask task:mTask){
//                task.cancel(false);
//            }
//        }
//
//    }

    //用来加载从start到end的所有图片
//    public void loadImages(int start,int end){
//        for(int i=start;i<end;i++){
//            String url=AudioAdapter.URLS[i];
//            Bitmap bitmap=getBitmapFromCache(url);
//            //如果缓存中没有，那么去下载
//            if(bitmap==null) {
//                AudioAsyncTask task=new AudioAsyncTask(url);
//                task.execute(url);
//                mTask.add(task);
//            }else{
//                ImageView imageView= (ImageView) mListView.findViewWithTag(url);
//                imageView.setImageBitmap(bitmap);
//            }
//        }
//    }



    private class AudioAsyncTask extends AsyncTask<String,Void,Bitmap>{

        private ImageView mImageView;
        private String mUrl;

        public AudioAsyncTask(ImageView imageView,String url){
            mImageView=imageView;
            mUrl=url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url=params[0];
            //从网络上获取图片
            Bitmap bitmap=getBitmapFromURL(url);
            if(bitmap!=null){
                //将不在缓存的图片加入缓存
                addBitmapToCache(url,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(mImageView.getTag().equals(mUrl)){
                mImageView.setImageBitmap(bitmap);
            }

        }
    }
}
