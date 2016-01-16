package com.example.fe.musicplayer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fe on 16-1-10.
 */
public class HomeActivity extends Activity implements RefreshListView.IRefreshListener,RefreshListView.ILoadListener{

//    private PullToRefreshListView pullToRefreshListView;
    private RefreshListView mlistView;
    private Button reload;
    private static String URL="http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(!isNetworkAvailable(this)){
            setContentView(R.layout.unavilable_network);
            Toast toast = Toast.makeText(getApplicationContext(), "网络不可用！", Toast.LENGTH_SHORT);
            toast.show();
            reload=(Button)findViewById(R.id.btn_reload);
            reload.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable(HomeActivity.this)) {
                        startActivity();
                    }
                }
            });
        }else
            startActivity();
    }

    public void startActivity(){
        setContentView(R.layout.home);
        mlistView=(RefreshListView)findViewById(R.id.listview);
        mlistView.setRefreshListenerInterface(this);
        mlistView.setLoadListenerInterface(this);

        new AudioAsyncTask().execute(URL);//执行listview加载任务
        mlistView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(HomeActivity.this,PlayerFragment.class);
                startActivity(intent);
            }
        });
    }

    //判断网络状态
    public boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info !=null && info.isAvailable());

    }


    //将url对应的JSON格式数据转化为我们所封装的AudioBean
    private List<AudioBean> getJsonData(String url){
        List<AudioBean> audioBeanList=new ArrayList<>();
        try{
        String jsonString=readStream(new URL(url).openStream());
            JSONObject jsonObject;
            AudioBean audioBean;
            try {
                jsonObject=new JSONObject(jsonString);
                JSONArray jsonArray=jsonObject.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    audioBean = new AudioBean();
                    audioBean.audioIconUrl = jsonObject.getString("picSmall");
                    audioBean.audioTitle = jsonObject.getString("name");
                    audioBean.audioContent = jsonObject.getString("description");
                    audioBeanList.add(audioBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return audioBeanList;
    }


    //通过is解析网页返回的数据
    private String readStream(InputStream is){
        InputStreamReader isr;
        String result="";
        try {
            String line="";
            isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            while((line=br.readLine())!=null){
                result+=line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



    //获取最新数据，通知页面显示，通知listview刷新完毕
    @Override
    public void onRefresh() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... params) {

                        return null;

                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        mlistView.refreshComplete();
                    }
                }.execute(new Void[]{});

            }
        }, 2000);

    }

    //获取更多数据，通知listview显示
    @Override
    public void onLoad() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        mlistView.loadComplete();
                    }
                }.execute(new Void[]{});

            }
        }, 2000);

    }


    //实现网络的异步访问
    class AudioAsyncTask extends AsyncTask<String,Integer,List<AudioBean>>{
        protected List<AudioBean> doInBackground(String...params){
            return getJsonData(params[0]);
        }

        protected  void onPostExecute(List<AudioBean> audioBean){
            super.onPostExecute(audioBean);
            AudioAdapter adapter=new AudioAdapter(HomeActivity.this,audioBean,mlistView);
            mlistView.setAdapter(adapter);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
