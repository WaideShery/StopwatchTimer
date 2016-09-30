package com.neirx.stopwatchtimer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;
import com.neirx.stopwatchtimer.utility.Stopwatch;

/**
 * Created by Waide Shery on 30.09.2016.
 *
 */
public class PlayPauseService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SettingsManagement settings = AppSettings.getInstance(this);

        boolean isRunNow = settings.getBoolPref(SettingPref.Bool.isStopwatchRun);
        long baseTime = settings.getLongPref(SettingPref.Long.stopwatchBaseTime, -1);
        long savedTime = settings.getLongPref(SettingPref.Long.stopwatchSavedTime, 0);
        long stopwatchTime = savedTime;

        if(baseTime != -1){
            stopwatchTime += System.currentTimeMillis() - baseTime;
        }

        if(isRunNow){
            baseTime = -1;
            savedTime = stopwatchTime;
            settings.setPref(SettingPref.Long.stopwatchBaseTime, baseTime);
            settings.setPref(SettingPref.Long.stopwatchSavedTime, savedTime);
            showPauseNotify(stopwatchTime);
        } else {
            baseTime = System.currentTimeMillis();
            settings.setPref(SettingPref.Bool.wasStopwatchStart, true);
            settings.setPref(SettingPref.Long.stopwatchBaseTime, baseTime);
            showPlayNotify(stopwatchTime);
        }

        settings.setPref(SettingPref.Bool.isStopwatchRun, !isRunNow);

        stopSelf();
        return START_NOT_STICKY;
    }

    private void showPauseNotify(long timeMillis){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.pause_stopwatch_widget);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.notify_icon).setContent(remoteViews);
        Intent resultIntent = new Intent(this, MainActivity.class);

        remoteViews.setChronometer(R.id.chronometer, timeMillis, "MM:SS", false);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent buttonPendingIntent = PendingIntent.getService(this, 78753,
                new Intent(this, PlayPauseService.class), PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.ibPlay, buttonPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(MainActivity.notifyId, mBuilder.build());
    }

    private void showPlayNotify(long timeMillis){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.play_stopwatch_widget);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.notify_icon).setContent(remoteViews);
        Intent resultIntent = new Intent(this, MainActivity.class);

        remoteViews.setChronometer(R.id.chronometer, timeMillis, null, true);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent buttonPendingIntent = PendingIntent.getService(this, 78753,
                new Intent(this, PlayPauseService.class), PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.ibPause, buttonPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(MainActivity.notifyId, mBuilder.build());
    }

}
