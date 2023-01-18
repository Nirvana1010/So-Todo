package com.vodka.sotodo;

public class CalenderBean {
    String startTime;
    String endTime;
    String eventTitle;
    String description;
    String location;

    long startEventTime;
    long endEventTime;
    long currentTime;
    long event_id;

    public CalenderBean(String st, String et, String evtitle, String des, String loc, long sEt, long eEt, long curT, long eid){
        this.startTime = st;
        this.endTime = et;
        this.description = des;
        this.eventTitle = evtitle;
        this.startEventTime = sEt;
        this.endEventTime = eEt;
        this.currentTime = curT;
        this.location = loc;
        this.event_id = eid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getStartEventTime() {
        return startEventTime;
    }

    public void setStartEventTime(long startEventTime) {
        this.startEventTime = startEventTime;
    }

    public long getEndEventTime() {
        return endEventTime;
    }

    public void setEndEventTime(long endEventTime) {
        this.endEventTime = endEventTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }
}
