package com.example.taskmanagerproject.main.daoClasses;

public class Task {

    private Integer id;
    private String task;
    private Integer importanceLevel;
    private boolean isDone;

    public Task(String task, Integer importanceLevel, boolean isDone){
        this.task = task;
        this.importanceLevel = importanceLevel;
        this.isDone = isDone;
    }

    public Task(Integer id, String task, Integer importanceLevel, boolean isDone){
        this(task, importanceLevel, isDone);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Integer getImportanceLevel() {
        return importanceLevel;
    }

    public void setImportanceLevel(Integer importanceLevel) {
        this.importanceLevel = importanceLevel;
    }
    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

}
