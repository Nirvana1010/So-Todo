package com.vodka.sotodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TimeCount extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    String name;
    ArrayList<Events> events;
    TextView t;
    RadioGroup duration;
    String durationSelect;
    EditText yearInput;
    EditText monthInput;
    EditText dayInput;
    TextView dayText;
    String year, month, day, date;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_count);

        duration = (RadioGroup) findViewById(R.id.radioGroup);
        yearInput = (EditText) findViewById(R.id.year);
        monthInput = (EditText) findViewById(R.id.monthInput);
        dayInput = (EditText) findViewById(R.id.dayInput);
        confirm = (Button) findViewById(R.id.ok);
        dayText = (TextView) findViewById(R.id.dayText);

        name = getIntent().getStringExtra("username");
        durationSelect = "day";

        eventsRepo repo = new eventsRepo(this);
        events = repo.getEventsList();


        duration.setOnCheckedChangeListener(this);

        yearInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                year = yearInput.getText().toString();
            }
        });

        monthInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                month = monthInput.getText().toString();
            }
        });

        dayInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                day = dayInput.getText().toString();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = year + "-" + month + "-" + day;
                Intent JumpToTimeshow = new Intent(TimeCount.this, TimeShow.class);
                JumpToTimeshow.putExtra("durationSelect", durationSelect);
                JumpToTimeshow.putExtra("dateInput", date);
                JumpToTimeshow.putExtra("username", name);
                startActivity(JumpToTimeshow);
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        switch (checkedId) {
            //一天
            case R.id.day:
                Toast.makeText(this, "一天", Toast.LENGTH_SHORT).show();
                durationSelect = "day";
                dayInput.setVisibility(View.VISIBLE);
                dayText.setVisibility(View.VISIBLE);
                break;
            //一周
            case R.id.week:
                Toast.makeText(this, "一周", Toast.LENGTH_SHORT).show();
                durationSelect = "week";
                dayInput.setVisibility(View.VISIBLE);
                dayText.setVisibility(View.VISIBLE);
                break;
            //一月
            case R.id.month:
                Toast.makeText(this, "一月", Toast.LENGTH_SHORT).show();
                durationSelect = "month";
                dayInput.setVisibility(View.GONE);
                dayText.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (TimeCount.this.getCurrentFocus() != null) {
                if (TimeCount.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(TimeCount.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

}
