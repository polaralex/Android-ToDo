package com.emexezidis.alex.ErasmusUoiApp.classes;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

public abstract class Card {

    protected AppCompatActivity callingActivity;
    private Intent intentForOnClick = null;
    private String title ="";
    private String description ="";
    private Integer icon;

    public Card(AppCompatActivity callingActivity) {
        this.callingActivity = callingActivity;
    }

    public abstract Intent getOnClickAction();
    public abstract void update();

    public boolean hasOnClickAction() {
        if (intentForOnClick != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setIcon(Integer icon) {
        this.icon = icon;
    }

    public Integer getIcon() {
        return icon;
    }

    protected void setIntentForOnClick(Intent intent) { intentForOnClick = intent; }

    public Intent getIntentForOnClick() { return intentForOnClick; }

    protected AppCompatActivity getCallingActivity() { return callingActivity; }

}
