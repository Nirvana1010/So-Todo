package com.vodka.sotodo;

public class Events {
    public int id;
    String username;
    public String date;     //日期
    public String event;    //事件
    public int time;        //持续时间(单位：min)

    Events() {}

    Events(String username, String date, String event, int time) {
        this.username = username;
        this.date = date;
        this.event = event;
        this.time = time;
    }

    public Events(int id, String username, String date, String event, int time) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.event = event;
        this.time = time;
    }
}
