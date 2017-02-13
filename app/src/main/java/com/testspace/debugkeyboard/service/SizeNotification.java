package com.testspace.debugkeyboard.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.renderscript.RenderScript;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.testspace.debugkeyboard.R;

public class SizeNotification {
    private final int notificationId;
    private final Context context;
    private final NotificationManager notificationManager;
    private final Service service;
    private String info;
    private int requestCode;
    private boolean notificationExists;


    public SizeNotification(int notificationId, Service service) {
        this.notificationId = notificationId;
        this.requestCode = notificationId;
        this.context = service.getApplicationContext();
        this.service = service;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public SizeNotification setInfo(String info) {
        this.info = info;
        return this;
    }

    public Notification build() {
        Intent intent = new Intent();
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(Notification.CATEGORY_ALARM)
                .setOngoing(true)
                .setContentTitle("Debug Keyboard")
                .setContentText("Size adjustment")
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        notification.contentView = new RemoteViews(context.getPackageName(), R.layout.size_notification);

        updateRemoteViews(notification);
        return notification;
    }

    private void updateRemoteViews(Notification notification) {
        RemoteViews rv = notification.contentView;

        rv.setTextViewText(R.id.notification_tvInfo, info);

        rv.setOnClickPendingIntent(R.id.notification_btnReset, pendingIntent(KeyboardService.ACTION_RESET));
        rv.setOnClickPendingIntent(R.id.notification_btnInc, pendingIntent(KeyboardService.ACTION_INCREASE));
        rv.setOnClickPendingIntent(R.id.notification_btnDec,pendingIntent(KeyboardService.ACTION_DECREASE));
    }

    private PendingIntent pendingIntent(String action) {
        return PendingIntent.getService(
                context, requestCode++,
                new Intent(context, KeyboardService.class)
                        .putExtra(KeyboardService.EXTRA_ACTION, action),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void update() {
        if (notificationExists) {
            notificationManager.notify(notificationId, build());
            notificationExists = true;
        } else {
            service.startForeground(notificationId, build());
        }
    }

    public void remove() {
        service.stopForeground(true);
        notificationManager.cancel(notificationId);
        notificationExists = false;
    }

    public boolean isVisible() {
        return notificationExists;
    }
}
