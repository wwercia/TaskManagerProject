package com.example.taskmanagerproject.main.daoClasses;

public class Today {

    private Integer id;
    private Integer day;

    public Today(Integer day) {
        this.day = day;
    }

    public Today(Integer id, Integer day) {
        this(day);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

}
