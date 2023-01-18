package com.vodka.sotodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class TimeManagement extends AppCompatActivity {
    String name;
    Spinner events;
    EditText hour;
    EditText minute;
    Button confirm;
    String eventSelect = "工作";
    String hourInput = null;
    String minuteInput = null;
    int duration;
    String[] eventList = {"工作", "学习", "运动", "休息", "吃饭", "玩乐", "其他"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_management);

        name = getIntent().getStringExtra("username");

        events = (Spinner) findViewById(R.id.spinner);
        hour = (EditText) findViewById(R.id.hour);
        minute = (EditText) findViewById(R.id.minute);
        confirm = (Button) findViewById(R.id.confirm);

        events.setSelection(0);
        events.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventSelect = eventList[position];
                TextView tv = (TextView)view;
                tv.setPadding(120, 0, 0, 0);
                //tv.setTextColor(Color.rgb(96,0,191));
                tv.setTextSize(20.0f);    //设置大小
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eventSelect = eventList[0];
            }
        });

        hour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hourInput = hour.getText().toString();
            }
        });

        minute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                minuteInput = minute.getText().toString();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventsRepo repo = new eventsRepo(TimeManagement.this);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String date = year + "-" + month + "-" + day;
                if(hourInput == null)
                    duration = Integer.valueOf(minuteInput);
                else if(minuteInput == null)
                    duration = Integer.valueOf(hourInput)*60;
                else
                    duration = Integer.valueOf(hourInput)*60 + Integer.valueOf(minuteInput);
                Events event = new Events(name, date, eventSelect, duration);
                int num = repo.insert(event);
                Toast.makeText(TimeManagement.this, ""+num, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //点击空白区域隐藏软键盘
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (TimeManagement.this.getCurrentFocus() != null) {
                if (TimeManagement.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(TimeManagement.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
