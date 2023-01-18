package com.vodka.sotodo;

public class ToDoList {
    private int id;  // ID

    private String title;  // 歌名
    private String date;  // 显示
    private String remark;

    ToDoList() { super(); }

    public ToDoList(int id, String title, String date, String remark) {
        super();
        this.id = id;

        this.title = title;
        this.date = date;
        this.remark = remark;
    }

    // 设置/获取各个属性
    void setId(int id) { this.id = id; }
    int getId() { return id; }

    void setTitle(String title) { this.title = title; }
    String getTitle() { return title; }

    void setDate(String date) { this.date = date; }
    String getDate() { return date; }

    void setRemark(String remark) { this.remark = remark; }
    String getRemark() { return remark; }

}
