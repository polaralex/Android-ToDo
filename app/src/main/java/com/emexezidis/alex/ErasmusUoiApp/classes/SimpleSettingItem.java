package com.emexezidis.alex.ErasmusUoiApp.classes;

public class SimpleSettingItem {

    private String title;
    private String data;

    public SimpleSettingItem(String title, String data) {
        this.title = title;
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setData (String title, String description) { this.title = title; this.data = description; }

    public String getData() {
        return data;
    }

}
