package com.vodka.sotodo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TimeShow extends AppCompatActivity {
    String dateInput;
    String durationSelect;
    String name;
    eventsRepo repo;
    ArrayList<Events> events;
    Map<String, Integer> eventsMap = new HashMap<>();
    Map<String, Integer> EndOfMonth = new HashMap<>();
    TextView title;
    TextView work;
    TextView study;
    TextView exercise;
    TextView rest;
    TextView eat;
    TextView play;
    TextView other;

    int totalTime = 0;
    int[] color = {Color.rgb(255,128,128), Color.rgb(255,255,128),
                    Color.rgb(128,255,128), Color.rgb(128,255,255),
                    Color.rgb(0,128,255), Color.rgb(255,128,255),
                    Color.rgb(255,128,192)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_show);

        dateInput = getIntent().getStringExtra("dateInput");
        durationSelect = getIntent().getStringExtra("durationSelect");
        name = getIntent().getStringExtra("username");

        eventsMap.put("工作", 0);
        eventsMap.put("学习", 1);
        eventsMap.put("运动", 2);
        eventsMap.put("休息", 3);
        eventsMap.put("吃饭", 4);
        eventsMap.put("玩乐", 5);
        eventsMap.put("其他", 6);
        int[] eventsTime = {0,0,0,0,0,0,0};
        float[] p = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};

        //每个月共有多少天
        EndOfMonth.put("1", 31);
        EndOfMonth.put("2", 28);
        EndOfMonth.put("3", 31);
        EndOfMonth.put("4", 30);
        EndOfMonth.put("5", 31);
        EndOfMonth.put("6", 30);
        EndOfMonth.put("7", 31);
        EndOfMonth.put("8", 31);
        EndOfMonth.put("9", 30);
        EndOfMonth.put("10", 31);
        EndOfMonth.put("11", 30);
        EndOfMonth.put("12", 31);

        SectorGraphView sectorGraphView = (SectorGraphView) findViewById(R.id.sector_graph);
        title = (TextView) findViewById(R.id.TS_message);
        work = (TextView) findViewById(R.id.work);
        study = (TextView) findViewById(R.id.study);
        exercise = (TextView) findViewById(R.id.exercise);
        rest = (TextView) findViewById(R.id.rest);
        eat = (TextView) findViewById(R.id.eat);
        play = (TextView) findViewById(R.id.play);
        other = (TextView) findViewById(R.id.other);

        repo = new eventsRepo(this);
        if(durationSelect.equals("day")) {
            events = repo.getDailyEvents(name, dateInput);
            if (events.size() == 0) {
                Toast.makeText(this, "无统计结果，请重新输入！", Toast.LENGTH_SHORT).show();
                return;
            }
            title.setText(dateInput+"：你的时间都在");
            for(Events event : events) {
                int tmp = event.time;
                eventsTime[eventsMap.get(event.event)] += tmp;
                totalTime += event.time;
                //Toast.makeText(this, "" + eventsTime[eventsMap.get(event.event)], Toast.LENGTH_SHORT).show();
            }
            for(int i = 0; i < 7; i++)
                    p[i] = (float)eventsTime[i]/totalTime;

            NumberFormat format= NumberFormat.getNumberInstance() ;
            format.setMaximumFractionDigits(2);
            work.setText(format.format(p[0]*100) + "%，共  " + eventsTime[0] + "  min");
            study.setText(format.format(p[1]*100) + "%，共  " + eventsTime[1] + "  min");
            exercise.setText(format.format(p[2]*100) + "%，共  " + eventsTime[2] + "  min");
            rest.setText(format.format(p[3]*100) + "%，共  " + eventsTime[3] + "  min");
            eat.setText(format.format(p[4]*100) + "%，共  " + eventsTime[4] + "  min");
            play.setText(format.format(p[5]*100) + "%，共  " + eventsTime[5] + "  min");
            other.setText(format.format(p[6]*100) + "%，共  " + eventsTime[6] + "  min");
            sectorGraphView.setProportion(p)
                    .setColor(color)
                    .draw();

        }
        else if(durationSelect.equals("week")) {
            String[] ss = dateInput.split("-");
            String day = ss[2];
            String dayNextweek = String.valueOf(Integer.parseInt(day)+6);
            //一周的期限若超过月底，取本月最后一天
            if(Integer.valueOf(dayNextweek) > EndOfMonth.get(ss[1]))
                dayNextweek = String.valueOf(EndOfMonth.get(ss[1]));
            title.setText(dateInput+" - "+ss[0]+"-"+ss[1]+"-"+dayNextweek+"，你的时间都在");

            events = repo.getMonthlyEvents(name, ss[0]+"-"+ss[1]+"%");
            //从一个月中找到符合要求的一周
            if(events.size() != 0) {
                int dayBegin = Integer.parseInt(day);
                int dayEnd = Integer.parseInt(dayNextweek);
                Iterator<Events> iterator = events.iterator();
                while(iterator.hasNext()) {
                    Events event = iterator.next();
                    String[] tmp = event.date.split("-");
                    //每一条数据的“日”
                    int dayInt = Integer.valueOf(tmp[2]);
                    //删去不符合一周期限日期的数据
                    if(dayInt < dayBegin || dayInt > dayEnd)
                        iterator.remove();
                }
            }
            if (events.size() == 0) {
                Toast.makeText(this, "无统计结果，请重新输入！", Toast.LENGTH_SHORT).show();
                return;
            }
            for(Events event : events) {
                int tmp = event.time;
                eventsTime[eventsMap.get(event.event)] += tmp;
                totalTime += event.time;
                //Toast.makeText(this, "" + eventsTime[eventsMap.get(event.event)], Toast.LENGTH_SHORT).show();
            }
            for(int i = 0; i < 7; i++)
                p[i] = (float)eventsTime[i]/totalTime;

            NumberFormat format= NumberFormat.getNumberInstance() ;
            format.setMaximumFractionDigits(2);
            work.setText(format.format(p[0]*100) + "%，共  " + eventsTime[0] + "  min");
            study.setText(format.format(p[1]*100) + "%，共  " + eventsTime[1] + "  min");
            exercise.setText(format.format(p[2]*100) + "%，共  " + eventsTime[2] + "  min");
            rest.setText(format.format(p[3]*100) + "%，共  " + eventsTime[3] + "  min");
            eat.setText(format.format(p[4]*100) + "%，共  " + eventsTime[4] + "  min");
            play.setText(format.format(p[5]*100) + "%，共  " + eventsTime[5] + "  min");
            other.setText(format.format(p[6]*100) + "%，共  " + eventsTime[6] + "  min");
            sectorGraphView.setProportion(p)
                    .setColor(color)
                    .draw();
        }
        else if(durationSelect.equals("month")) {
            String[] ss = dateInput.split("-");
            title.setText(ss[0]+" - "+ss[1]+" 月，你的时间都在");
            events = repo.getMonthlyEvents(name, ss[0]+"-"+ss[1]+"%");
            if (events.size() == 0) {
                Toast.makeText(this, "无统计结果，请重新输入！", Toast.LENGTH_SHORT).show();
                return;
            }
            for(Events event : events) {
                int tmp = event.time;
                eventsTime[eventsMap.get(event.event)] += tmp;
                totalTime += event.time;
                //Toast.makeText(this, "" + eventsTime[eventsMap.get(event.event)], Toast.LENGTH_SHORT).show();
            }
            for(int i = 0; i < 7; i++)
                p[i] = (float)eventsTime[i]/totalTime;

            NumberFormat format= NumberFormat.getNumberInstance() ;
            format.setMaximumFractionDigits(2);
            work.setText(format.format(p[0]*100) + "%，共  " + eventsTime[0] + "  min");
            study.setText(format.format(p[1]*100) + "%，共  " + eventsTime[1] + "  min");
            exercise.setText(format.format(p[2]*100) + "%，共  " + eventsTime[2] + "  min");
            rest.setText(format.format(p[3]*100) + "%，共  " + eventsTime[3] + "  min");
            eat.setText(format.format(p[4]*100) + "%，共  " + eventsTime[4] + "  min");
            play.setText(format.format(p[5]*100) + "%，共  " + eventsTime[5] + "  min");
            other.setText(format.format(p[6]*100) + "%，共  " + eventsTime[6] + "  min");
            sectorGraphView.setProportion(p)
                    .setColor(color)
                    .draw();

        }
    }
}
