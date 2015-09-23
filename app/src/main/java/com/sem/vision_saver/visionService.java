package com.sem.vision_saver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Chronometer;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class visionService extends Service {

    public static final long INTERVAL_WORK = 10 * 1000; // 10 seconds в релизе 1800000 30минут
    public static final long INTERVAL_REST = 5 * 1000; // 5 seconds
    public static final int NOTIFICATION_ID = 1;
    public static boolean isWork = false;
    static boolean isExit;

    private Handler mHandler = new Handler();
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }
        MainActivity.togBut.setChecked(true);
        Shedulya();
    }

    private void Shedulya () {
        if (!isWork) {
            mTimer.schedule(new TimeDisplayTimerTask(), INTERVAL_REST);
        } else {
            mTimer.schedule(new TimeDisplayTimerTask(), INTERVAL_WORK);
        }
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    Noty();
                }
            });
        }
    }

    private PendingIntent getActivityPendingIntent() {
        Intent activityIntent = new Intent(this, MainActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void Noty() {

        Intent intent = new Intent(this, visionService.class);
        PendingIntent pIntent = PendingIntent.getService(this, 0, intent, 0);
        Notification notificationW = new NotificationCompat.Builder(this)


                .setTicker("work")
                .setContentTitle("work")
                .setContentText("Коснитесь для проджолжения")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pIntent)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);


        Notification notificationR = new NotificationCompat.Builder(this)

                .setTicker("rest")
                .setContentTitle("rest")
                .setContentText("Коснитесь для проджолжения")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pIntent)
                .build();

        if(!isWork)
        {notificationManager.notify(NOTIFICATION_ID, notificationW); isWork = true; MainActivity.togBut.setChecked(false);}
        else
        {notificationManager.notify(NOTIFICATION_ID, notificationR); isWork = false; MainActivity.togBut.setChecked(false);}
        Shedulya();
    }

    public void onDestroy(){
        mTimer.cancel();
    }
}
