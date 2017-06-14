package com.emexezidis.alex.ErasmusUoiApp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.classes.Place;

public class PlaceInfoScreen extends AppCompatActivity {

    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_info_layout);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.place_info_activity_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        mPlace = intent.getParcelableExtra("place");

        initializeUserInterface();
    }

    private void initializeUserInterface() {

        TextView title = (TextView) findViewById(R.id.placeTitle);
        TextView description = (TextView) findViewById(R.id.placeDescription);
        ImageView timeIcon = (ImageView) findViewById(R.id.time_icon);
        TextView timeDescription = (TextView) findViewById(R.id.time_text);

        title.setText(mPlace.getName());
        description.setText(mPlace.getDescription());
        timeIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_access_time_black_24dp));
        timeDescription.setText(mPlace.getOpenTime());
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
