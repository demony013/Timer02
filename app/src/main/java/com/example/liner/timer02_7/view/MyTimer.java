package com.example.liner.timer02_7.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by liner on 16/7/29.
 */
public class MyTimer extends View {

    private final String TAG = "MyTimer";

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap = null;
    private Canvas bitCanvas = null;
    private ValueAnimator anim;
    private float x;
    private int b;

    private int r1 = 170,r2 = 245,r3 = 400;

    public MyTimer(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void initAnimator(){
        if (anim !=null && anim.isRunning()){
            anim.cancel();
            anim.start();
        }else {
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    x = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            anim.setInterpolator(new LinearInterpolator());
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.scale(1,-1);

        if (bitmap == null){
            bitmap = Bitmap.createBitmap(800,800, Bitmap.Config.ARGB_8888);
            bitCanvas = new Canvas(bitmap);
        }else {

        }

        paint.setStrokeWidth(2);
        canvas.drawLine(-r3,0,r3,0,paint);
        canvas.drawLine(0,-r3,0,r3,paint);

        bitCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        drawOnCanvas(bitCanvas);
        canvas.drawBitmap(bitmap,-r3,-r3,paint);

    }

    protected void drawOnCanvas(Canvas canvas){

        Path path = new Path();
        RectF rectF = new RectF(r3-r2,r3-r2,r3+r2,r3+r2);
        paint.setAlpha(255);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(75);

        path.addArc(rectF,90,x%360);

        canvas.drawPath(path,paint);

        paint.setAlpha(200);
        RectF rectF1 = new RectF(r3-r1,r3-r1,r3+r1,r3+r1);
        path.addArc(rectF1,90,x/b);
        canvas.drawPath(path,paint);
        path.close();
    }

    public void setColor(int color){
        paint.setColor(color);
        invalidate();
    }

    public void setTime(int f){
        b=f;
        x=360*b-1;
        anim=ValueAnimator.ofFloat(f*360,0).setDuration(f*60*1000);
        initAnimator();
    }

    public void setStart(){
        anim.start();
    }

    public void onPause(){
        if (anim !=null && anim.isRunning()){
            anim.end();
        }
    }

    public void setInit(){
        b=1;
        x=360*b-1;
        invalidate();
    }
}
