package com.usnschool.miniproject_englishword;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by it on 2016-07-19.
 */
public class RecentAdapter extends BaseAdapter {

    private Context ctx;
    private int layout;
    private ArrayList<String> word;
    private ArrayList<String> meaning;
    private ArrayList<String> date;
    private LayoutInflater inflater;

    public RecentAdapter(Context ctx, int layout, ArrayList<String> word, ArrayList<String> meaning, ArrayList<String> date) {
        this.ctx = ctx;
        this.layout = layout;
        this.word = word;
        this.meaning = meaning;
        this.date = date;
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return word.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(layout, viewGroup, false);
        }

        TextView textview1 = (TextView)view.findViewById(R.id.recentsub);
        textview1.setText(word.get(i).toString());

        TextView textview2 = (TextView)view.findViewById(R.id.recentsub2);
        textview2.setText(meaning.get(i).toString());

        TextView textview3 = (TextView)view.findViewById(R.id.recentsub3);
        textview3.setText(date.get(i).toString());

        return view;
    }
}

