package com.emexezidis.alex.ErasmusUoiApp.activities;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.emexezidis.alex.ErasmusUoiApp.R;
import com.emexezidis.alex.ErasmusUoiApp.classes.Step;
import com.emexezidis.alex.ErasmusUoiApp.fragments.DatePickerFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

public class StepInfoScreen extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, DatePickerDialog.OnDateSetListener {

    private Step step;
    private int stepPosition;

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    private float latitude;
    private float longitude;

    private FloatingActionButton floatingActionButton;
    private TextView stepName;

    private MediaPlayer confirmSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_info_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setElevation(0);

        getIntentExtras();

        manageMapFragmentVisibility();
        manageChildrenLayoutVisibility();

        confirmSound = MediaPlayer.create(this, R.raw.blip1);

        stepName = (TextView) findViewById(R.id.stepInfoName);
        TextView stepDescription = (TextView) findViewById(R.id.stepInfoDescription);
        TextView stepUrl = (TextView) findViewById(R.id.stepInfoUrl);

        stepName.setText(step.getName());
        // The following replacing happens because the parser sees the new line 'escaped'
        stepDescription.setText(step.getDescription().replace("\\n", "\n"));
        stepUrl.setText(step.getUrl());

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabStepDone);
        floatingActionButton.setOnClickListener(this);

        setActivityColorsDynamically();
    }

    private void manageChildrenLayoutVisibility() {

        if(!step.getDescription().isEmpty()) {
            findViewById(R.id.stepInfoDescriptionContainer).setVisibility(View.VISIBLE);
        }

        if(!step.getUrl().isEmpty()) {
            findViewById(R.id.stepInfoInternetLink_container).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.stepinfoscreen_toolbar_menu, menu);
        return true;
    }

    private void getIntentExtras() {

        Intent intent = getIntent();
        step = intent.getParcelableExtra("step");
        stepPosition = intent.getExtras().getInt("stepPosition");
    }

    @Override
    public void onClick(View v) {

        if(v == findViewById(R.id.fabStepDone)){

            step.changeState();

            if (step.getState()) {
                playConfirmSound();
            }

            setActivityColorsDynamically();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                returnStepToProcessActivity();
                break;

            case R.id.menuItem_setNotification:
                showDatePickerDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setActivityColorsDynamically() {

        View view = findViewById(R.id.stepInfoHeaderContainer);

        if (step.getState()) {

            stepName.setPaintFlags(stepName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
            view.setBackgroundColor(getResources().getColor(R.color.green));
            floatingActionButton.setImageResource(R.drawable.ic_x_cross_white_icon);

            changeStatusBarColor(R.color.green_darker);

        } else {

            stepName.setPaintFlags(stepName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

            // Resetting the color values:
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.europe_blue)));
            view.setBackgroundColor(getResources().getColor(R.color.europe_blue));
            floatingActionButton.setImageResource(R.drawable.ic_done_white_24dp);

            changeStatusBarColor(R.color.europe_blue_darker);
        }

        view.invalidate();
    }

    private void playConfirmSound() {
        confirmSound.start();
    }

    private void changeStatusBarColor(Integer color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, color));
        }
    }

    @Override
    public void onBackPressed() {
        returnStepToProcessActivity();
        super.onBackPressed();
    }

    private void returnStepToProcessActivity() {

        Intent returnIntent = getIntent();

        returnIntent.putExtra("returnedStep", step);
        returnIntent.putExtra("stepPosition", stepPosition);

        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void manageMapFragmentVisibility() {

        setupMapFragment();

        if (step.isGeolocationAvailable()) {

            latitude = step.getLatitude();
            longitude = step.getLongitude();

            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(mapFragment)
                    .commit();

        } else {

            fragmentManager.beginTransaction().hide(mapFragment).commit();
            View fragmentLayout = findViewById(R.id.mapFragment);

            if (fragmentLayout != null) {
                fragmentLayout.setVisibility(View.GONE);
            }
        }
    }

    private void setupMapFragment() {

        fragmentManager = getFragmentManager();

        mapFragment = (MapFragment) fragmentManager
                .findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        LatLng targetCoordinate = new LatLng(latitude, longitude);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(targetCoordinate)
                .zoom(15)
                .build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        map.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude)));

        map.getUiSettings().setMapToolbarEnabled(false);
        map.moveCamera(cameraUpdate);
    }

    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        final Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.YEAR, year);
        selectedDate.set(Calendar.MONTH, monthOfYear);
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        createAddToCalendarIntent(selectedDate);
    }

    private void createAddToCalendarIntent(Calendar selectedDate) {

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, selectedDate.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, selectedDate.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                .putExtra(CalendarContract.Events.TITLE, getEventName())
                .putExtra(CalendarContract.Events.DESCRIPTION, step.getDescription())
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);
    }

    private String getEventName() {
        return (getString(R.string.erasmus_reminder)+step.getName());
    }

}
