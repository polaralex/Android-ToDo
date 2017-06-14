package com.emexezidis.alex.ErasmusUoiApp.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.emexezidis.alex.ErasmusUoiApp.R;

import java.util.Calendar;

public class NotificationManager extends AppCompatActivity implements View.OnClickListener {

    private DatePicker datePicker;

    // Explain: This Class is not currently used, because I moved its functionality to a solution that
    // uses a Calendar Intent to create events. Still, I'm keeping this as a working implementation
    // of a Notification system.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_manager);
        getIntentExtras();

        //datePicker = (DatePicker) findViewById(R.id.timePicker);

        Button setTimeButton = (Button) findViewById(R.id.setTimeForNotificationButton);
        setTimeButton.setOnClickListener(this);
    }

    private void getIntentExtras() {

        Intent intent = getIntent();
        // Todo: Get information from the Steps list about the Alarm that is being set.
    }

    @Override
    public void onClick(View v) {

        if(v == findViewById(R.id.setTimeForNotificationButton)) {

            Calendar date = getCalendarFromPicker();

            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

            showToast("Notification Set!");

            alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), alarmIntent);
        }
    }

    private Calendar getCalendarFromPicker() {

        Calendar calendar = Calendar.getInstance();

//        Integer year = calendar.get(Calendar.YEAR);
//        Integer month = calendar.get(Calendar.MONTH);
//        Integer day = calendar.get(Calendar.DAY_OF_MONTH);
//        Integer hour = timePicker.getCurrentHour();
//        Integer minute = timePicker.getCurrentMinute();
//
//        System.out.println(year.toString()+"-"+month.toString()+"-"+day.toString()+" "+hour.toString()+":"+minute.toString());

        //calendar = new GregorianCalendar(year, month, day, hour, minute);

        return calendar;
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
