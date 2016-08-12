package com.usnschool.miniproject_englishword;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

/**
 * Created by it on 2016-07-21.
 */
public class MyView extends View {

    private final int STATE_NOTDID = 0;
    private final int STATE_CORRECT = 1;
    private final int STATE_INCORRECT = 2;
    private ArrayList<Integer> answerlist;
    private int position = 0;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = null;
        if(answerlist != null){
            switch(answerlist.get(position)){
                case STATE_NOTDID:
                    break;

                case STATE_CORRECT:
                    paint = new Paint();
                    paint.setColor(Color.BLUE);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(10);
                    canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, canvas.getWidth()*0.4f, paint);
                    break;

                case STATE_INCORRECT:
                    paint = new Paint();
                    paint.setColor(Color.RED);
                    paint.setStrokeWidth(10);
                    canvas.drawLine(canvas.getWidth()/10, canvas.getHeight()/10, canvas.getWidth()*9/10, canvas.getHeight()*9/10, paint);
                    canvas.drawLine(canvas.getWidth()*9/10, canvas.getHeight()/10, canvas.getWidth()/10, canvas.getHeight()*9/10, paint);
                    break;
            }
        }
    }

    public void setSTATE(int position, ArrayList<Integer> answerlist){
        this.position = position;
        this.answerlist = answerlist;
    }
}