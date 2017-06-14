package com.emexezidis.alex.ErasmusUoiApp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.classes.ActionSettingItem;
import com.emexezidis.alex.ErasmusUoiApp.classes.DataUploadStatusSettingsItem;
import com.emexezidis.alex.ErasmusUoiApp.classes.RestoreSettingsItem;
import com.emexezidis.alex.ErasmusUoiApp.classes.ShowUserDataSettingsItem;
import com.emexezidis.alex.ErasmusUoiApp.classes.SimpleSettingItem;
import com.emexezidis.alex.ErasmusUoiApp.adapters.SettingsAdapter;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    private ArrayList<SimpleSettingItem> settingList;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSharedPreferences = getApplicationContext().getSharedPreferences("Erasmus Prefs", 0);

        initializeSettingsList();

        ListView listview = (ListView) findViewById(R.id.settingsListView);
        final SettingsAdapter adapter = new SettingsAdapter(this, settingList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if((adapter.getItem(position) instanceof ActionSettingItem)) {

                    ((ActionSettingItem)adapter.getItem(position)).doAction();

                } else {
                    Snackbar.make(getCurrentFocus(), R.string.settings_change_data, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initializeSettingsList() {

        settingList = new ArrayList<>();
        settingList.add(new ShowUserDataSettingsItem(this));
        settingList.add(new DataUploadStatusSettingsItem(getSharedBooleanPreference("dataIsUploaded"), getSharedPreference("uploadDataUserChoice"), this));
        settingList.add(new RestoreSettingsItem(this));
    }

    private String getSharedPreference(String preferenceName){
        return (mSharedPreferences.getString(preferenceName, null));
    }

    private Boolean getSharedBooleanPreference(String preferenceName) {
        return (mSharedPreferences.getBoolean(preferenceName, false));
    }

    // This is used to provide back navigation through the toolbar
    // for 4.0 Android devices:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
