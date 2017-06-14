package com.emexezidis.alex.ErasmusUoiApp.classes;

/**
 * Created by alex on 27/10/16.
 */

public abstract class ActionSettingItem extends SimpleSettingItem {

    public ActionSettingItem(String title, String data) {
        super(title, data);
    }

    public abstract void doAction();
}
