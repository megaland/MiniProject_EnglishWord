package com.usnschool.miniproject_englishword;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * Created by it on 2016-07-19.
 */
public class PageAdapter extends PagerAdapter{

    private Context ctx;
    private ArrayList<String> quizword;
    private ArrayList<String> quizmeaning;
    private LayoutInflater inflater;
    private Button btn1, btn2, btn3, btn4;
    private ViewPager pager;
    private LinkedHashSet<String> hsset = new LinkedHashSet();
    private ArrayList<Integer>  answerlist;
    private LinkedList<MyView> myviewlist = new LinkedList<>();
    public PageAdapter(Context ctx, ArrayList<String> quizword, ArrayList<String> quizmeaning, ViewPager pager, ArrayList<Integer> answerlist) {
        this.ctx = ctx;
        this.quizword = quizword;
        this.quizmeaning = quizmeaning;
        this.pager = pager;
        this.answerlist = answerlist;
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return quizword.size();
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        int layoutfactor = (int)(Math.random() * 2);

        if(layoutfactor == 0 ){
            int answernum = (int)(Math.random()*4);
            boolean flag = true;
            while(hsset.size() < 4){
                int hssetrandomnum = (int)(Math.random()*quizmeaning.size());
                if(answernum==hsset.size() && flag){
                    hsset.add(quizmeaning.get(position));
                    flag = false;
                } else if (!(quizmeaning.get(hssetrandomnum).equals(quizmeaning.get(position)))){
                    hsset.add(quizmeaning.get(hssetrandomnum));

                }
            }

            Iterator<String> iterator = hsset.iterator();

            view = inflater.inflate(R.layout.quizlayoutsubone, container, false);


            EditText edittext1 = (EditText)view.findViewById(R.id.quizoneedit1);
            edittext1.setText(quizword.get(position));
            EditText edittext2 = (EditText)view.findViewById(R.id.quizoneedit2);
            if(answerlist.get(position)==1){
                edittext2.setText(quizmeaning.get(position));
            }else {
                edittext2.setText("?");
            }

            MyView myview = (MyView)view.findViewById(R.id.view2);
            myview.setSTATE(position, answerlist);
            myview.postInvalidate();
            myviewlist.add(myview);
            if(myviewlist.size() > 5){
                myviewlist.remove(0);
            }


            Button resetbutton = (Button)view.findViewById(R.id.hiddenresetbutton);
            final MyView myviewf = myview;
            resetbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < answerlist.size(); i++) {
                        answerlist.set(i, 0);
                    }
                    for (int i = 0; i < myviewlist.size(); i++) {
                        myviewlist.get(i).postInvalidate();
                    }
                }
            });

            btn1 = (Button)view.findViewById(R.id.button);
            btn1.setText(iterator.next());
            btn1.setOnClickListener(new MyEvent(answernum, position, layoutfactor, edittext1, edittext2, myview));
            btn2 = (Button)view.findViewById(R.id.button2);
            btn2.setText(iterator.next());
            btn2.setOnClickListener(new MyEvent(answernum, position, layoutfactor, edittext1, edittext2, myview));
            btn3 = (Button)view.findViewById(R.id.button3);
            btn3.setText(iterator.next());
            btn3.setOnClickListener(new MyEvent(answernum, position, layoutfactor, edittext1, edittext2, myview));
            btn4 = (Button)view.findViewById(R.id.button4);
            btn4.setText(iterator.next());
            btn4.setOnClickListener(new MyEvent(answernum, position, layoutfactor, edittext1, edittext2, myview));

        }else if(layoutfactor == 1){
            int answernum = (int)(Math.random()*4);
            boolean flag = true;
            while(hsset.size() < 4){
                int hssetrandomnum = (int)(Math.random()*quizword.size());
                if(answernum==hsset.size() && flag){
                    hsset.add(quizword.get(position));
                    flag = false;
                } else if(!(quizword.get(hssetrandomnum).equals(quizword.get(position)))) {
                    hsset.add(quizword.get(hssetrandomnum));
                }
            }

            Iterator<String> iterator = hsset.iterator();

            view = inflater.inflate(R.layout.quizlayoutsubone, container, false);
            EditText edittext1 = (EditText)view.findViewById(R.id.quizoneedit1);
            if(answerlist.get(position)==1){
                edittext1.setText(quizword.get(position));
            }else {
                edittext1.setText("?");
            }
            EditText edittext2 = (EditText)view.findViewById(R.id.quizoneedit2);
            edittext2.setText(quizmeaning.get(position));

            MyView myview = (MyView)view.findViewById(R.id.view2);
            myview.setSTATE(position, answerlist);
            myview.postInvalidate();
            myviewlist.add(myview);
            if(myviewlist.size() > 5){
                myviewlist.remove(0);
            }

            Button resetbutton = (Button)view.findViewById(R.id.hiddenresetbutton);
            final MyView myviewf = myview;
            resetbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < answerlist.size(); i++) {
                        answerlist.set(i, 0);
                    }
                    for (int i = 0; i < myviewlist.size(); i++) {
                        myviewlist.get(i).postInvalidate();
                    }
                }
            });

            btn1 = (Button)view.findViewById(R.id.button);
            btn1.setText(iterator.next());
            btn1.setOnClickListener(new MyEvent(answernum, position, layoutfactor, edittext1, edittext2, myview));
            btn2 = (Button)view.findViewById(R.id.button2);
            btn2.setText(iterator.next());
            btn2.setOnClickListener(new MyEvent(answernum, position, layoutfactor, edittext1, edittext2, myview));
            btn3 = (Button)view.findViewById(R.id.button3);
            btn3.setText(iterator.next());
            btn3.setOnClickListener(new MyEvent(answernum, position, layoutfactor, edittext1, edittext2, myview));
            btn4 = (Button)view.findViewById(R.id.button4);
            btn4.setText(iterator.next());
            btn4.setOnClickListener(new MyEvent(answernum, position, layoutfactor, edittext1, edittext2, myview));
        }
        hsset.clear();

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    class MyEvent implements View.OnClickListener{

        private int answernum;
        private int position;
        private int layoutfactor;
        private EditText edittext1;
        private EditText edittext2;
        private final int STATE_NOTDID = 0;
        private final int STATE_CORRECT = 1;
        private final int STATE_INCORRECT = 2;
        private MyView myview;
        public MyEvent(int answernum, int position, int layoutfactor, EditText edittext1, EditText edittext2, MyView myview) {
            this.answernum = answernum;
            this.position = position;
            this.layoutfactor = layoutfactor;
            this.edittext1 = edittext1;
            this.edittext2 = edittext2;
            this.myview = myview;
        }

        @Override
        public void onClick(View view) {

            int buttonnum = 0;

            switch(view.getId()){
                case R.id.button:
                    buttonnum = 0;
                    break;
                case R.id.button2:
                    buttonnum = 1;
                    break;
                case R.id.button3:
                    buttonnum = 2;
                    break;
                case R.id.button4:
                    buttonnum = 3;
                    break;
            }

            if(answernum==buttonnum){
                pager.setCurrentItem(pager.getCurrentItem()+1);

                if(layoutfactor == 0 ){
                    edittext2.setText(quizmeaning.get(position));
                }else{
                    edittext1.setText(quizword.get(position));
                }
                answerlist.set(position, STATE_CORRECT);
                myview.setSTATE(position, answerlist);

                myview.postInvalidate();
            }else{
                answerlist.set(position, STATE_INCORRECT);
                myview.setSTATE(position, answerlist);

                myview.postInvalidate();
            }
        }
    }



}
