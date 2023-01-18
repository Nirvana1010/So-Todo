package com.vodka.sotodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    private final static int STORGE_REQUEST = 1 ;  //权限申请码requestCode
    private ListView event_List;
    private List<CalenderBean> eventInfoList;
    private MyAdapter adapter;
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_view);
        button1 = findViewById(R.id.getInfo);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先检查自身是否已经拥有相关权限，拥有则不再重复申请
                int check = checkSelfPermission(Manifest.permission.WRITE_CALENDAR);
                //没有相关权限
                if (check != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    ActivityCompat.requestPermissions(FirstActivity.this,
                            new String[]{Manifest.permission.WRITE_CALENDAR,
                                    Manifest.permission.READ_CALENDAR}, STORGE_REQUEST);
                } else {
                    //已有权限的情况下可以直接初始化程序
                    initView();
                }
            }
        });

    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case STORGE_REQUEST :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //完成程序的初始化
                    initView();
                    System.out.println("程序申请权限成功，完成初始化") ;
                }
                else {
                    System.out.println("程序没有获得相关权限，请处理");
                }
                break ;
        }
    }

    private void initView(){
        setContentView(R.layout.first_view);
        event_List = findViewById(R.id.listview);
        eventInfoList = new ArrayList<>();
        eventInfoList = CalenderUtil.queryCalendarEvent(this);
        adapter = new MyAdapter(this,eventInfoList);
        event_List.setAdapter(adapter);

        event_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String str1 = eventInfoList.get(position).eventTitle;
                String str2 = eventInfoList.get(position).startTime;
                String str3 = eventInfoList.get(position).endTime;
                long longong = eventInfoList.get(position).event_id;
                String str4 = Long.toString(longong);

                intent.setClass(FirstActivity.this,SecActivity.class);
                intent.putExtra("title",str1);
                intent.putExtra("start_time",str2);
                intent.putExtra("end_time",str3);
                intent.putExtra("position",position);
                intent.putExtra("evID",str4);
                startActivity(intent);
            }
        });
    }

}
