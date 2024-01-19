package com.example.taskmanagerproject.main.daoClasses;

public class Book {

    private Integer id;
    private String name;
    private boolean isRead;
    private String opinion;

    public Book(String name, boolean isRead, String opinion){
        this.name = name;
        this.isRead = isRead;
        this.opinion = opinion;
    }

    public Book(Integer id, String name, boolean isRead, String opinion){
        this(name, isRead, opinion);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

}
