package com.example.taskmanagerproject.main.daoClasses;

import java.util.Date;

public class Event {

    private Integer id;
    private String startTime;
    private String endTime;
    private Integer day;
    private String month;
    private String event;

    public Event(String startTime, String endTime, Integer day, String month, String event){
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.month = month;
        this.event = event;
    }

    public Event(Integer id, String startTime, String endTime, Integer day, String month, String event){
        this(startTime, endTime, day, month, event);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

}
