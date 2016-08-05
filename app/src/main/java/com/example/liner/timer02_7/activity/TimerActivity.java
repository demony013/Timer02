package com.example.liner.timer02_7.activity;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liner.timer02_7.R;
import com.example.liner.timer02_7.adapter.TimerAdapter;
import com.example.liner.timer02_7.animation.TurnAnimation;
import com.example.liner.timer02_7.db.TimerDB;
import com.example.liner.timer02_7.model.Timer02;
import com.example.liner.timer02_7.view.MyTimer;
import com.example.liner.timer02_7.view.RoundRectLinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by liner on 16/7/29.
 */
public class TimerActivity extends Activity implements View.OnClickListener{

    private final String TAG = "TimerActivity";


    private RoundRectLinearLayout btn;
    private LinearLayout list_btn;
    private TextView sum_show;
    private ListView listView;
    private MyTimer myTimer;
    private TextView time_show;

    private TurnAnimation turnA;

    private boolean state = false;

    private Timer mTimer;
    private int t,atime;
    private int p=0;

    private int startColor = 0xff8bc34a;
    private int endColor   = 0xffff5722;

    private TimerAdapter adapter;
    private List<Timer02> datalist = new ArrayList<Timer02>();
    private TimerDB timerDB;
    private List<Timer02> timer02List;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int sum;
    private boolean item_state;
    private boolean doing;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.timer_layout);
        init();




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                final Timer02 timer02 = timer02List.get(position);
                Log.d(TAG,""+position);


                LinearLayout list_layout = (LinearLayout) view.findViewById(R.id.list_layout);
                list_layout.setPivotY(0);
                if (list_layout.getVisibility()==View.GONE){
                    list_layout.setVisibility(View.VISIBLE);
                    item_state=false;
                    turnX(list_layout,-90,0);

                }else{
                    item_state=true;
                    turnX(list_layout,0,-90);

                }




            }
        });

    }

    public void turnX(final View view, float s, float e){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotationX",s,e);
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (item_state){
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private void init(){
        btn = (RoundRectLinearLayout) findViewById(R.id.btn);
        list_btn = (LinearLayout) findViewById(R.id.list_btn);
        sum_show = (TextView) findViewById(R.id.sum);
        listView = (ListView) findViewById(R.id.list_view);
        myTimer = (MyTimer) findViewById(R.id.timer);
        time_show = (TextView) findViewById(R.id.time_show);

        btn.setColor(0xffff5722);
        mTimer = new Timer();
        setColorAnimation(myTimer,startColor,endColor);
        myTimer.setInit();
        myTimer.setTime(25);
        atime = 1500;
        time_show.setText("25:00");
        doing= false;

        adapter = new TimerAdapter(this,R.layout.list_item,datalist);
        listView.setAdapter(adapter);
        timerDB = TimerDB.getInstance(this);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        queryTime();

        btn.setOnClickListener(this);
        list_btn.setOnClickListener(this);
        myTimer.setOnClickListener(this);
    }

    private void queryTime(){
        timer02List = timerDB.loadTheTimer();
        if (timer02List.size()>0){
            datalist.clear();
            sum = 0;
            for (Timer02 timer02:timer02List){
                sum++;
                datalist.add(timer02);

            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);

        }else {
            listView.setVisibility(View.GONE);
        }
        sum_show.setText("Sum  is  "+sum);
    }

    private void setTimerTask(){
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
                //Log.d(TAG,""+t);
            }
        },600,997);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            int msgId = msg.what;
            switch (msgId){
                case 1:
                    t++;
                    int b= atime - t;

                    if (b == 0){

                        Message message = new Message();
                        message.what = 2;
                        mHandler.sendMessage(message);
                    }
                    if (b/60>9){
                        if (b%60>9){
                            time_show.setText(b/60+":"+b%60);
                        }else{
                            time_show.setText(b/60+":0"+b%60);
                        }
                    }else {
                        if (b%60>9){
                            time_show.setText("0"+b/60+":"+b%60);
                        }else{
                            time_show.setText("0"+b/60+":0"+b%60);
                        }
                    }
                    break;
                case 2:

                    chooseState();
                    t=0;
                    p++;
                    if (state){
                        sum++;
                        sum_show.setText("Sum  is  "+sum);
                        Timer02 timer02 = new Timer02();
                        timer02.setTime(sdf.format(new Date()));
                        timerDB.saveTimer(timer02);
                    }

                    mTimer.cancel();

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn:
                //sum.setText("btn" + "");
                if (!doing){
                    Log.e(TAG,""+doing);
                    mTimer = new Timer();
                    setTimerTask();
                    myTimer.setStart();
                    t=0;
                    doing =true;
                }else {
                    //doing = false;
                }


                break;
            case R.id.list_btn:

                if (listView.getVisibility()==View.GONE){
                    queryTime();
                    listView.setVisibility(View.VISIBLE);
                    //动画载入list子项
                    Animation anim_in = AnimationUtils.loadAnimation(this,R.anim.item_animation);
                    LayoutAnimationController lac_in = new LayoutAnimationController(anim_in);
                    lac_in.setOrder(LayoutAnimationController.ORDER_NORMAL);
                    lac_in.setDelay(0.2f);

                    listView.setLayoutAnimation(lac_in);

                }else {
                    //动画退出list
                    Animation anim_out = AnimationUtils.loadAnimation(this,R.anim.item_animation_out);
                    LayoutAnimationController lac_out = new LayoutAnimationController(anim_out);
                    lac_out.setOrder(LayoutAnimationController.ORDER_NORMAL);
                    lac_out.setDelay(0.2f);
                    listView.setLayoutAnimation(lac_out);

                    anim_out.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            listView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }
                queryTime();
                break;
            case R.id.timer:
                doing=false;
                chooseState();
                break;
            default:
                break;
        }
    }

    private void chooseState(){

        if(state){
            setOneState();
            state=false;
        }else {
            if (p<5){
                setTwoState();
            }else {
                setThreeState();
                p=0;
            }

            state=true;
        }
        mTimer.cancel();
    }

    private void applyRotation(float start,float end,View v){

        final float centerX = v.getWidth()/2.0f;
        final float centerY = v.getHeight()/2.0f;

        turnA = new TurnAnimation(this,start,end,centerX,centerY,1.0f,true);
        turnA.setDuration(1000);
        turnA.setFillAfter(true);
        turnA.setInterpolator(new LinearInterpolator());

        turnA.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(turnA);
    }

    private void setColorAnimation(View v,int startColor,int endColor){

        final ObjectAnimator colorAnimator = ObjectAnimator.ofArgb(v,"color",startColor,endColor);
        colorAnimator.setDuration(1000);
        colorAnimator.setInterpolator(new LinearInterpolator());
        colorAnimator.start();

    }



    private void setOneState(){
        applyRotation(180,0,myTimer);
        startColor = 0xff8bc34a;
        endColor = 0xffff5722;
        setColorAnimation(myTimer,startColor,endColor);
        setColorAnimation(btn,startColor,endColor);
        myTimer.onPause();
        myTimer.setTime(25);
        atime = 1500;
        time_show.setText("25:00");
    }
    private void setTwoState(){
        applyRotation(0,180,myTimer);
        startColor = 0xffff5722;
        endColor = 0xff8bc34a;
        setColorAnimation(myTimer,startColor,endColor);
        setColorAnimation(btn,startColor,endColor);
        myTimer.onPause();
        myTimer.setTime(5);
        atime = 300;
        time_show.setText("05:00");
    }
    private void setThreeState(){
        applyRotation(0,180,myTimer);
        startColor = 0xffff5722;
        endColor = 0xff8bc34a;
        setColorAnimation(myTimer,startColor,endColor);
        setColorAnimation(btn,startColor,endColor);
        myTimer.onPause();
        myTimer.setTime(15);
        atime = 900;
        time_show.setText("15:00");
    }

}
