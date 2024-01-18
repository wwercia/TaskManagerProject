package com.example.taskmanagerproject.main.daoClasses;

public class Day {

    private Integer id;
    private Integer number;
    private String name;
    private String month;
    private Integer week;

    public Day(Integer number, String name, String month, Integer week){
        this.number = number;
        this.name = name;
        this.month = month;
        this.week = week;
    }

    public Day(Integer id, Integer number, String name, String month, Integer week){
        this(number, name, month, week);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

}
