package com.example.liner.timer02_7.adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liner.timer02_7.R;
import com.example.liner.timer02_7.activity.TimerActivity;
import com.example.liner.timer02_7.application.TimerApplication;
import com.example.liner.timer02_7.application.TimerContext;
import com.example.liner.timer02_7.db.TimerDB;
import com.example.liner.timer02_7.model.Timer02;

import java.util.List;

/**
 * Created by liner on 16/7/31.
 */
public class TimerAdapter extends ArrayAdapter<Timer02>{

    private int resourceId;
    private TimerDB timerDB;

    public TimerAdapter(Context context, int textViewResourceId, List<Timer02> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        timerDB = TimerDB.getInstance(TimerApplication.getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Timer02 timer02 = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.timer_time = (TextView) view.findViewById(R.id.list_time);
            //0801
            viewHolder.list_layout = (LinearLayout) view.findViewById(R.id.list_layout);
            viewHolder.text = (TextView) view.findViewById(R.id.list_text2);
            viewHolder.btn = (TextView) view.findViewById(R.id.btn_test);
            viewHolder.edit = (EditText) view.findViewById(R.id.list_edit);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.timer_time.setText(timer02.getTime()+"  "+timer02.getLabel());
        viewHolder.text.setText(timer02.getLabel());
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //viewHolder.text.setText(viewHolder.edit.getText());
                if (viewHolder.edit.getVisibility()==View.VISIBLE) {
                    viewHolder.edit.setVisibility(View.GONE);
                    viewHolder.text.setVisibility(View.VISIBLE);
                    viewHolder.btn.setText("编辑");
                    //text.setText(edit.getText());
                    viewHolder.text.setText(viewHolder.edit.getText());
                    viewHolder.timer_time.setText(timer02.getTime()+"  "+viewHolder.edit.getText());
                    timerDB.updateLabel(timer02.getId(),viewHolder.edit.getText().toString());

                }else{
                    viewHolder.edit.setVisibility(View.VISIBLE);
                    viewHolder.text.setVisibility(View.GONE);
                    viewHolder.text.setText(viewHolder.edit.getText());
                    viewHolder.btn.setText("确定");
                }
                viewHolder.edit.setText("标签");

            }
        });
        return view;
    }

    class ViewHolder {
        TextView timer_time;
        TextView text;
        //0801
        LinearLayout list_layout;

        TextView btn;
        EditText edit;

    }
}
