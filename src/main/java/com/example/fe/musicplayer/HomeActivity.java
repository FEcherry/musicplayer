package com.example.fe.musicplayer;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.widget.SimpleCursorAdapter;

/**
 * Created by fe on 16-1-10.
 */
public class HomeActivity extends ActionBarActivity implements OnItemClickListener{

    private PullToRefreshListView pullToRefreshListView;
    private ArrayAdapter<String> adapter;
    private ListView mlistView;
    private View view1,view2,view3;
    private LayoutInflater inflater;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        String[] from ={"id","title","date","time"};
        int[] to =
        adapter= new SimpleCursorAdapter(this,R.layout.home,,c,from,to);
        mlistView.setAdapter(adapter);
        mlistView=(ListView)findViewById(R.id.listview);




        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView);
        pullToRefreshListView.setOnItemClickListener(this);
        pullToRefreshListView.setMode(Mode.BOTH);
        pullToRefreshListView.getLoadingLayoutProxy(false,true).setRefreshingLabel("正在加载更多");
        pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>(){
            public void onRefresh(PullToRefreshBase<ListView> refreshView){
                String labelString =DateUtils.formatDateTime(getApplicationContext().getApplicationContext(),System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME|DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(labelString);
                new GetDataTask().execute();
            }
        });
        return view;
    }

    class GetDataTask extends AsyncTask<String,Integer,String>{
        protected String doInBackground(String...params){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
            }
            String str="最新的内容";
            return str;
        }

        protected void onPostExecute(String result){
            if(pullToRefreshListView.isHeaderShown()){
                linkedList.addFirst(result);
            }else if(pullToRefreshListView.isFooterShown()){
                linkedList.addLast(result);
            }
        adapter.notifyDataSetChanged();
        pullToRefreshListView.onRefreshComplete();
        super.onPostExecute(result);
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
