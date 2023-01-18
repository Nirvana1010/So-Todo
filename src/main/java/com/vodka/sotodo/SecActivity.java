package com.vodka.sotodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SecActivity extends AppCompatActivity {
    Button button1,button2;
    String title;
    String start_time;
    String end_time;
    String event_id;
    long longong;
    int pos;

    private TextView receive_tx1, receive_tx2, receive_tx3;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_view);
        button1 = findViewById(R.id.idit);
        button2 = findViewById(R.id.delete);
        receive_tx1 = findViewById(R.id.item_re_title);
        receive_tx2 = findViewById(R.id.item_re_start_time);
        receive_tx3 = findViewById(R.id.item_re_end_time);

        /**
         * 从intent中获取信息并显示在TextView控件中
         */
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        title = bundle.getString("title");
        start_time = bundle.getString("start_time");
        end_time = bundle.getString("end_time");
        pos = bundle.getInt("position");
        event_id = bundle.getString("evID");
        longong = Long.parseLong(event_id);

        receive_tx1.setText(title);
        receive_tx2.setText(start_time);
        receive_tx3.setText(end_time);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用删除函数
                Del(longong);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m_intent = new Intent(Intent.ACTION_VIEW);
                m_intent.setClass(SecActivity.this,TirdActivity.class);
                m_intent.putExtra("ed_title",title);
                m_intent.putExtra("ed_start_time",start_time);
                m_intent.putExtra("ed_end_time",end_time);
                m_intent.putExtra("sysid",event_id);
                startActivity(m_intent);
            }
        });
    }

    private void Del(long idd){
        List<CalenderBean> beanList = CalenderUtil.queryCalendarEvent(this);
        int ch = CalenderUtil.deleteCalendarEvent(this, idd);
        if(ch>0){
            Toast.makeText(this,"删除成功",Toast.LENGTH_LONG).show();
        }
    }

}
