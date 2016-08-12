package com.usnschool.miniproject_englishword;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentManager;

/**
 * Created by it on 2016-07-19.
 */
public class DBConnector extends SQLiteOpenHelper{
    private Context ctx;
    public DBConnector(Context context) {
        super(context, "EnglishWord.db", null, 1);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table EnglishWordTBL (num integer primary key AUTOINCREMENT, word text, meaning text, exam text , date Integer, quiznum Integer)";
        String sql2 = "create table EnglishWordTWO (checkdate text)";
        String sql3 = "insert into EnglishWordTWO (checkdate) values ('20160721')";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists EnglishWordTBL");
        sqLiteDatabase.execSQL("drop table if exists EnglishWordTWO");
        onCreate(sqLiteDatabase);
    }

    public Context getCtx(){
        return ctx;
    }
}
