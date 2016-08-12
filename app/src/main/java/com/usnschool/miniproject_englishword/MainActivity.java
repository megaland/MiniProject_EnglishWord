package com.usnschool.miniproject_englishword;

import android.app.Fragment;
import android.app.FragmentTransaction;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private TabLayout tablayout;
    private FragmentTransaction ft;
    private ListFragment listfragment;
    private RecentFragment recentfragment;
    private QuizFragment quizfragment;
    private RepeatFragment repeatfragment;
    private AddFragment addfragment;
    private android.app.FragmentManager fm;
    private static DBConnector connector = null;
    private static ArrayList<String> quizword = new ArrayList<String>();
    private static ArrayList<String> quizmeaning = new ArrayList<String>();
    private static ArrayList<Integer> answerlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("영어단어암기");
        setSupportActionBar(toolbar);

        tablayout = (TabLayout)findViewById(R.id.tablayout);
        tablayout.addTab(tablayout.newTab().setText("목록"));
        tablayout.addTab(tablayout.newTab().setText("최근"));
        tablayout.addTab(tablayout.newTab().setText("퀴즈"));
        tablayout.addTab(tablayout.newTab().setText("반복"));
        tablayout.addTab(tablayout.newTab().setText("추가"));

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        listfragment = new ListFragment();
        recentfragment = new RecentFragment();
        quizfragment = new QuizFragment();
        repeatfragment = new RepeatFragment();
        addfragment = new AddFragment();

        connector = new DBConnector(this);
        SQLiteDatabase db = connector.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from EnglishWordTWO", null);
        cursor.moveToNext();
        String datecheck = cursor.getString(0);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String sql = "select * from EnglishWordTBL order by date desc limit 30";
        if(!((sdf.format(date)).equals(datecheck))){

            cursor = db.rawQuery(sql, null);
            String updatesql = "";
            db = connector.getWritableDatabase();
            while(cursor.moveToNext()){
                updatesql = "update EnglishWordTBL set date =  '"+sdf.format(date) + "' where word = '"+cursor.getString(4) +"'";
                db.execSQL(updatesql);

            }
            updatesql = "update EnglishWordTWO set checkdate = '"+sdf.format(date)+"'";
            db = connector.getReadableDatabase();
        }



        cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            quizword.add(cursor.getString(1));
            quizmeaning.add(cursor.getString(2));
            answerlist.add(cursor.getInt(5));
        }

        tablayout.setOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch(tab.getPosition()){
            case 0:
                ft.replace(R.id.mainlayout, listfragment);
                break;
            case 1:
                ft.replace(R.id.mainlayout, recentfragment);
                break;
            case 2:
                ft.replace(R.id.mainlayout, quizfragment);
                break;
            case 3:
                ft.replace(R.id.mainlayout, repeatfragment);
                break;
            case 4:
                ft.replace(R.id.mainlayout, addfragment);
                break;
        }
        ft.commit();
        ft = fm.beginTransaction();

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public static class ListFragment extends Fragment {

        private int NORMAL_MODE = 0;
        private int DELETE_MODE = 1;
        private ListView listview;
        private ListlistAdapter listlistadapter;
        private ArrayList<String> word;
        private ArrayList<String> meaning;
        private boolean flag = false;
        private SQLiteDatabase db;
        private LinkedList<Integer> deletelist = new LinkedList<>();
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.listlayout, container, false);
            renewarraylist();

            Button deletebutton = (Button)view.findViewById(R.id.deletebutton);
            deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!flag){
                        listlistadapter = new ListlistAdapter(connector.getCtx(), R.layout.listlayoutsub, word, meaning, DELETE_MODE, deletelist);
                        listview.setAdapter(listlistadapter);
                        flag = true;
                    }else{
                        for (int i = 0; i < deletelist.size(); i++) {
                            db.delete("EnglishWordTBL", "word = '"+word.get(deletelist.get(i))+"'", null);
                        }
                        deletelist.clear();
                        flag = false;
                        renewarraylist();
                        listlistadapter = new ListlistAdapter(connector.getCtx(), R.layout.listlayoutsub, word, meaning, NORMAL_MODE, deletelist);
                        listview.setAdapter(listlistadapter);

                    }

                }
            });

            listview = (ListView)view.findViewById(R.id.listlistView);

            listlistadapter = new ListlistAdapter(connector.getCtx(), R.layout.listlayoutsub, word, meaning, NORMAL_MODE, deletelist);
            listview.setAdapter(listlistadapter);

            return view;
        }

        public void renewarraylist(){
            db = connector.getReadableDatabase();
            String sql = "select * from EnglishWordTBL order by word asc";
            Cursor cursor = db.rawQuery(sql, null);
            word = new ArrayList<>();
            meaning = new ArrayList<>();
            while(cursor.moveToNext()){
                word.add(cursor.getString(1));
                meaning.add(cursor.getString(2));
            }
        }
    }

    public static class RecentFragment  extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.recentlayout, container, false);
            SQLiteDatabase db = connector.getReadableDatabase();
            String sql = "select * from EnglishWordTBL order by date desc";
            Cursor cursor = db.rawQuery(sql, null);
            ArrayList<String> word = new ArrayList<String>();
            ArrayList<String> meaning = new ArrayList<String>();
            ArrayList<String> date = new ArrayList<String>();
            while(cursor.moveToNext()){
                word.add(cursor.getString(1));
                meaning.add(cursor.getString(2));
                date.add(cursor.getString(4));
            }
            ListView listview = (ListView)view.findViewById(R.id.recentlistView);
            RecentAdapter recentadapter = new RecentAdapter(connector.getCtx(), R.layout.recentlayoutsub, word, meaning, date);
            listview.setAdapter(recentadapter);
            return view;
        }
    }

    public static class QuizFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.quizlayout, container, false);
            ViewPager pager = (ViewPager)view.findViewById(R.id.view);
            PageAdapter adapter = new PageAdapter(connector.getCtx(), quizword, quizmeaning, pager, answerlist);
            pager.setAdapter(adapter);

            return view;
        }

    }

    public static class RepeatFragment extends Fragment {

        private TextView textview;
        private Thread thread;
        private Button startbtn;
        private Button resetbtn;
        private int count = -1;
        private int count2;
        private boolean flag = true;
        private Handler handler = new Handler(){


            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    if(count%2 == 0){
                        textview.setText(quizword.get(count/2));
                    }else {
                        textview.setText(quizmeaning.get(count/2));

                    }
                }else {
                    textview.setText(4-count2+"초");
                }
            }
        };

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.repeatlayout, container, false);
            textview = (TextView)view.findViewById(R.id.repeattextview);
            startbtn = (Button)view.findViewById(R.id.repeatstart);
            resetbtn = (Button)view.findViewById(R.id.repeatreset);

            startbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thread = new Thread(){
                        @Override
                        public void run() {
                            while(true){
                                count2++;
                                if(count2 > 3){
                                    count++;
                                    if(count/2>=quizword.size()){
                                        count = 0;
                                    }
                                    handler.sendEmptyMessage(1);
                                    SystemClock.sleep(2000);
                                }else {
                                    SystemClock.sleep(1000);
                                    handler.sendEmptyMessage(0);
                                }
                                if(!flag){
                                    break;
                                }
                            }
                        }
                    };
                    flag = true;
                    thread.start();
                    startbtn.setEnabled(false);
                }
            });
            resetbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count = -1;
                    flag = false;
                    startbtn.setEnabled(true);
                    textview.setText("리셋");
                }
            });

            return view;
        }
}


    public static class AddFragment extends Fragment {
        private EditText edittext1, edittext2, edittext3;
        private Button quizsavebutton;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.addlayout, container, false);

            edittext1 = (EditText) view.findViewById(R.id.addeditText);
            edittext2 = (EditText) view.findViewById(R.id.addeditText2);
            edittext3 = (EditText) view.findViewById(R.id.addeditText3);

            quizsavebutton = (Button) view.findViewById(R.id.savequizbutton);

            Button btn = (Button)view.findViewById(R.id.addbutton);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SQLiteDatabase db = connector.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("word", edittext1.getText().toString());
                    values.put("meaning", edittext2.getText().toString());
                    values.put("exam", edittext3.getText().toString());
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    values.put("date", sdf.format(date));
                    values.put("quiznum", 0);
                    db.insertOrThrow("EnglishWordTBL", null, values);
                    resetArraylists();
                    Toast.makeText(connector.getCtx(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    edittext1.setText("");
                    edittext2.setText("");
                    edittext3.setText("");
                }
            });

            quizsavebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SQLiteDatabase db = connector.getWritableDatabase();
                    for (int i = 0; i < answerlist.size(); i++) {
                        String sql = "update EnglishWordTBL set quiznum =  "+answerlist.get(i)+" where word = '"+quizword.get(i)+"'";
                        db.execSQL(sql);
                    }
                    resetArraylists();
                    Toast.makeText(connector.getCtx(), "퀴즈 내용이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }

        public void resetArraylists(){
            SQLiteDatabase db = connector.getReadableDatabase();
            String sql = "select * from EnglishWordTBL order by date desc limit 30";
            Cursor cursor = db.rawQuery(sql, null);
            quizword.clear();
            quizmeaning.clear();
            answerlist.clear();
            while(cursor.moveToNext()){
                quizword.add(cursor.getString(1));
                quizmeaning.add(cursor.getString(2));
                answerlist.add(cursor.getInt(5));
            }
        }
    }

}