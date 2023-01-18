package com.vodka.sotodo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TirdActivity extends AppCompatActivity {

    private Button button;
    private EditText editText, editText1, editText2;

    String title, start_time, end_time, sysID;   //修改前的参数
    String ch_title, ch_startime, ch_endtime;    //修改后的参数，保存
    long longong1;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_view);
        button = findViewById(R.id.save);
        editText = findViewById(R.id.temp_title);
        editText1 = findViewById(R.id.ed_start_time);
        editText2 = findViewById(R.id.ed_end_time);


        Intent my_intent = getIntent();
        Bundle bundle = my_intent.getExtras();
        title = bundle.getString("ed_title");
        start_time = bundle.getString("ed_start_time");
        end_time = bundle.getString("ed_end_time");
        sysID = bundle.getString("sysid");
        longong1 = Long.parseLong(sysID);

        editText.setText(title);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().toString()!=null){
                    String str = editText.getText().toString();
                    ch_title = str;
                }
                else
                    ch_title = title;
            }
        });

        editText1.setText(start_time);
        editText2.setText(end_time);

        Calendar calendar = Calendar.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch_startime = editText1.getText().toString();
                String ed_year_str = ch_startime.substring(0,4);
                String ed_month_str = ch_startime.substring(4,6);
                String ed_day_str = ch_startime.substring(6,8);
                String ed_hour_str = ch_startime.substring(8,10);
                String ed_min_str = ch_startime.substring(11,13);
                ch_startime = ed_year_str+"-"+ed_month_str+"-"+ed_day_str+" "+ed_hour_str+":"+ed_min_str+":00";

                ch_endtime = editText2.getText().toString();
                String ed_year_str2 = ch_endtime.substring(0,4);
                String ed_month_str2 = ch_endtime.substring(4,6);
                String ed_day_str2 = ch_endtime.substring(6,8);
                String ed_hour_str2 = ch_endtime.substring(8,10);
                String ed_min_str2 = ch_endtime.substring(11,13);
                ch_endtime = ed_year_str2+"-"+ed_month_str2+"-"+ed_day_str2+" "+ed_hour_str2+":"+ed_min_str2+":00";
                Update(longong1,ch_title,ch_startime,ch_endtime);
            }
        });
    }

    private void Update(long id, String ti, String st, String et){
        int sh = CalenderUtil.updateCalendar(this,ti,id,st, et);
        if(sh>0){
            Toast.makeText(this,"修改成功",Toast.LENGTH_LONG).show();
        }
    }

}
