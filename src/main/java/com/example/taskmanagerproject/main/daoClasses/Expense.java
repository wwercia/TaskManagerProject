package com.example.taskmanagerproject.main.daoClasses;

public class Expense {
    private Integer id;
    private String date;
    private Integer amount;
    private String plusOrMinus;
    private String description;


    public Expense(String date, Integer amount, String plusOrMinus, String spentOn) {
        this.date = date;
        this.amount = amount;
        this.plusOrMinus = plusOrMinus;
        this.description = spentOn;
    }

    public Expense(Integer id, String date, Integer amount, String plusOrMinus, String spentOn) {
        this(date,amount,plusOrMinus,spentOn);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPlusOrMinus() {
        return plusOrMinus;
    }

    public void setPlusOrMinus(String plusOrMinus) {
        this.plusOrMinus = plusOrMinus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
