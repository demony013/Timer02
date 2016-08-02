package com.example.liner.timer02_7.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.ContextThemeWrapper;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by liner on 16/7/29.
 */
public class TurnAnimation extends Animation {

    private final String TAG="TurnAnimation";

    private final float mFromDegrees;//开始角度
    private final float mToDegrees;//结束角度

    //中心点
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ; //深度

    //是否扭曲
    private final boolean mReverse;
    //摄像头
    private Camera mCamera;

    ContextThemeWrapper context;//活动类的上下文环境

    float scale = 1;//初始化

    public TurnAnimation(ContextThemeWrapper context,float fromDegrees,float toDegrees,float centerX,float centerY,float depthZ,boolean reverse){
        this.context = context;
        mFromDegrees = fromDegrees;
        mToDegrees   = toDegrees;
        mCenterX     = centerX;
        mCenterY     = centerY;
        mDepthZ      = depthZ;
        mReverse     = reverse;

        //获得手机屏幕比(dp与px的比例)
        scale = context.getResources().getDisplayMetrics().density;
    }

    //初始化
    @Override
    public void initialize(int width,int height,int parentWidth,int parentHeight){
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    //生成Transformation
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t){
        final float fromDegrees = mFromDegrees;

        //生成中间角度
        float degrees = fromDegrees + ((mToDegrees - fromDegrees)*interpolatedTime);
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        final Matrix matrix = t.getMatrix();

        camera.save();

        if (mReverse){
            camera.translate(0.0f,0.0f,mDepthZ*interpolatedTime);
        }else {
            camera.translate(0.0f,0.0f,mDepthZ*(1-interpolatedTime));
        }

        camera.rotateX(degrees);
        //取得变换后的矩阵
        camera.getMatrix(matrix);
        camera.restore();

        float[] mValues = {0,0,0,0,0,0,0,0,0};
        matrix.getValues(mValues);
        mValues[6] = mValues[6]/scale;
        matrix.setValues(mValues);

        matrix.preTranslate(-centerX,-centerY);
        matrix.postTranslate(centerX,centerY);


    }
}
