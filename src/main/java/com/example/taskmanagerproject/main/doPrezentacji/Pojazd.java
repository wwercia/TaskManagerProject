package com.example.taskmanagerproject.main.doPrezentacji;

public class Pojazd {
    private String marka;
    private String model;

    public Pojazd(String marka, String model) {
        this.marka = marka;
        this.model = model;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void jedz() {
        System.out.println("Pojazd jedzie.");
    }
}