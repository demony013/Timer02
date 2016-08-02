package com.example.liner.timer02_7.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by liner on 16/7/29.
 */
public class TimerApplication extends Application {

    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        if (!TimerContext.isInitilized()){
            TimerContext.init(getApplicationContext());
        }

        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
