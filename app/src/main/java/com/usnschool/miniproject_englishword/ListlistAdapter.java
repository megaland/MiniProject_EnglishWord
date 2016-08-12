package com.usnschool.miniproject_englishword;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by it on 2016-07-19.
 */
public class ListlistAdapter extends BaseAdapter{

    private Context ctx;
    private int layout;
    private ArrayList<String> word;
    private ArrayList<String> meaning;
    private LayoutInflater inflater;
    private LinkedList<Integer> deletelist;
    private int mode;

    public ListlistAdapter(Context ctx, int layout, ArrayList<String> word, ArrayList<String> meaning, int MODE, LinkedList<Integer> deletelist) {
        this.ctx = ctx;
        this.layout = layout;
        this.word = word;
        this.meaning = meaning;
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mode = MODE;
        this.deletelist = deletelist;
        Log.i("리스트어댑터생성자","생성");
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

        TextView textview1 = (TextView)view.findViewById(R.id.listword);
        textview1.setText(word.get(i).toString());

        TextView textview2 = (TextView)view.findViewById(R.id.listmeaning);
        textview2.setText(meaning.get(i).toString());

        CheckBox chbox= null;
        final Integer inumf = i;
        if (mode==1){
            chbox = (CheckBox)view.findViewById(R.id.checkBox);
            chbox.setVisibility(View.VISIBLE);
            chbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        deletelist.add(inumf);
                    }else{
                        deletelist.remove((Object)inumf);
                    }
                    Log.i("deletelist", deletelist.toString());
                }
            });
        }

        return view;
    }
}
