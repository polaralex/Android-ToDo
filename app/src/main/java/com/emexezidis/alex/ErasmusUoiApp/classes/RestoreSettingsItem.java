package com.emexezidis.alex.ErasmusUoiApp.classes;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.activities.Settings;
import com.emexezidis.alex.ErasmusUoiApp.services.UserDataHandler;

import static android.app.Activity.RESULT_OK;

public class RestoreSettingsItem extends ActionSettingItem {

    private AppCompatActivity callingActivity;

    public RestoreSettingsItem(AppCompatActivity callingActivity) {
        super(callingActivity.getString(R.string.delete_data_1), callingActivity.getString(R.string.delete_data_2));
        this.callingActivity = callingActivity;
    }

    @Override
    public void doAction() {

        // Show an Alert Dialog to explain the consequences of this action (deletion of data):
        AlertDialog alertDialog = new AlertDialog.Builder(callingActivity).create();
        alertDialog.setTitle(callingActivity.getString(R.string.settings_restore_user_data));
        alertDialog.setMessage(callingActivity.getString(R.string.delete_data_prompt));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, callingActivity.getString(R.string.delete_all_data),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        doDeleteData();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, callingActivity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }

    private void doDeleteData() {

        UserDataHandler.resetData(callingActivity);
        callingActivity.setResult(RESULT_OK, callingActivity.getIntent());
        callingActivity.finish();
    }
}
