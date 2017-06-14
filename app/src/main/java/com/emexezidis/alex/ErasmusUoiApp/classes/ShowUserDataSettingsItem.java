package com.emexezidis.alex.ErasmusUoiApp.classes;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.fragments.SimpleDialogFragment;

public class ShowUserDataSettingsItem extends ActionSettingItem {

    private AppCompatActivity callingActivity;

    public ShowUserDataSettingsItem(AppCompatActivity callingActivity) {
        super(callingActivity.getString(R.string.settings_user_data_title), callingActivity.getString(R.string.settings_user_data_description));
        this.callingActivity = callingActivity;
    }

    @Override
    public void doAction() {
        FragmentManager fm = callingActivity.getSupportFragmentManager();
        SimpleDialogFragment dialogFragment = new SimpleDialogFragment ();
        dialogFragment.show(fm, "User Data");
    }
}
