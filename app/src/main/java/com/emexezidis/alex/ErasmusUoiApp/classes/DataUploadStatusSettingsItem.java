package com.emexezidis.alex.ErasmusUoiApp.classes;

import android.support.v7.app.AppCompatActivity;

import com.emexezidis.alex.ErasmusUoiApp.R;

public class DataUploadStatusSettingsItem extends ActionSettingItem {

    public DataUploadStatusSettingsItem(boolean dataIsUploaded, String uploadDataAgreement, AppCompatActivity callingActivity) {

        super(callingActivity.getString(R.string.settings_data_upload_status_title), "Waiting for info...");

        if(uploadDataAgreement.equals("yes")) {
            if (dataIsUploaded) {
                super.setData(callingActivity.getString(R.string.data_upload_1), callingActivity.getString(R.string.upload_data_2));
            } else {
                super.setData(callingActivity.getString(R.string.upload_data_3), callingActivity.getString(R.string.upload_data_4));
            }
        } else {
            super.setData(callingActivity.getString(R.string.upload_data_5), callingActivity.getString(R.string.upload_data_6));
        }
    }

    @Override
    public void doAction() {

    }
}
