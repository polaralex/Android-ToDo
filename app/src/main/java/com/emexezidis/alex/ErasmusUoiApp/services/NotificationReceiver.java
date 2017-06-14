package com.emexezidis.alex.ErasmusUoiApp.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.emexezidis.alex.ErasmusUoiApp.activities.MainScreen;
import com.emexezidis.alex.ErasmusUoiApp.R;

public class NotificationReceiver extends BroadcastReceiver {

    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;

        Intent resultIntent = new Intent(mContext, MainScreen.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.globe_icon)
                .setContentTitle("Notification Title")
                .setContentText("Notification Description")
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager mNotificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(002, mBuilder.build());
    }

}
