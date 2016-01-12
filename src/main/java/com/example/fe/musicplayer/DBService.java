package com.example.fe.musicplayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

/**DBService是SQLiteOpenHelper类的子类，用于操作数据库
 * Created by fe on 16-1-12.
 */
public class DBService extends SQLiteOpenHelper {
    private final static int DATABASE_VERSION = 1;
    private final static String DATANAME_NAME = "test.db";

    public DBService(Context context){
        super(context,DATANAME_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE[t_text]("+"[_id]AUTOINC,"+"[name]VARCHAR2(20) NOT NULL CONFLICT FAIL,"
                +"CONSTRAINT[sqlite_autoindex_te_test_1]PRIMARY KEY([_id]))";
        db.execSQL(sql);
        //向test数据库中插入20条记录
        Random random = new Random();
        for(int i=0;i<20;i++){
            String s="";
            for(int j=0;j<10;j++){
                char c=(char)(97+random.nextInt(26));
                s+=c;
            }
            db.execSQL("insert into t_text(name)values(?)",new Object[]{s});
        }

    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

    public Cursor query(String sql,String[] args){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,args);
        return cursor;
    }


}
