package com.example.liner.timer02_7.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by liner on 16/7/29.
 */
public class RoundRectLinearLayout extends LinearLayout {

    private final String TAG = "RoundRectLinearLayout";

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    private String color = null;

    public RoundRectLinearLayout(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);


        path.addRoundRect(0,0,getWidth(),getHeight(),30,30,Path.Direction.CCW);
        paint.setStyle(Paint.Style.FILL);
        //paint.setColor(Color.parseColor(color));
        canvas.drawPath(path,paint);
    }



    public void setColor(int color){
        paint.setColor(color);
        invalidate();
    }
}
