package com.example.liner.timer02_7.application;

import android.content.Context;

/**
 * Created by liner on 16/7/29.
 */
public class TimerContext {

    private static TimerContext instance;

    private Context applicationContext;

    //获得应用实例
    public static TimerContext getInstance(){
        if (instance == null){
            throw new RuntimeException(TimerContext.class.getSimpleName() + " has not been initialized!");
        }
        return instance;
    }
    //全局Context
    public Context getApplicationContext(){
        return applicationContext;
    }

    public TimerContext(Context applicationContext){
        this.applicationContext = applicationContext;
    }

    //全局信息 只能调用一次
    public static void init(Context applicationContext){
        if (instance != null){
            throw  new RuntimeException(TimerContext.class.getSimpleName() + " can not be initialized multiple times!");
        }
        instance = new TimerContext(applicationContext);
    }
    //应用实例是否存在
    public static boolean isInitilized(){
        return (instance != null);
    }
}
