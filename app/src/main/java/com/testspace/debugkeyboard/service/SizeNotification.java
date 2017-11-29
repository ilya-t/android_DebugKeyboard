package com.testspace.debugkeyboard.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.testspace.debugkeyboard.R;

public class SizeNotification {
    private static final String CHANNEL_ID = "sie_notification_channel";
    private static final String CHANNEL_DESC = "Keyboard size notification";
    private static final long[] NO_VIBRATE = {};
    private static final Uri NO_SOUND = null;

    private final int notificationId;
    private final Context context;
    private final NotificationManager notificationManager;
    private final Service service;
    private final Bitmap icMinus;
    private final Bitmap icPlus;
    private final Bitmap icRestore;
    private String info;
    private int requestCode;
    private boolean notificationExists;


    public SizeNotification(int notificationId, Service service) {
        this.notificationId = notificationId;
        this.requestCode = notificationId;
        this.context = service.getApplicationContext();
        this.service = service;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        icMinus = vectorToBitmap(R.drawable.ic_minus);
        icPlus = vectorToBitmap(R.drawable.ic_plus);
        icRestore = vectorToBitmap(R.drawable.ic_restore);
    }

    private Bitmap vectorToBitmap(@DrawableRes int drawableId) {
        Drawable d = context.getResources().getDrawable(drawableId);
        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(),
                Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        d.draw(canvas);
        return bitmap;
    }

    public SizeNotification setInfo(String info) {
        this.info = info;
        return this;
    }

    public Notification build() {
        Intent intent = new Intent();
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        prepareNotificationChannel();
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true)
                .setSound(NO_SOUND)
                .setVibrate(NO_VIBRATE)
                .setContentTitle("Debug Keyboard")
                .setContentText("Size adjustment")
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        notification.contentView = new RemoteViews(context.getPackageName(), R.layout.size_notification);

        updateRemoteViews(notification);
        return notification;
    }

    private void prepareNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_DESC,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updateRemoteViews(Notification notification) {
        RemoteViews rv = notification.contentView;

        rv.setTextViewText(R.id.notification_tvInfo, info);
        rv.setBitmap(R.id.notification_btnInc, "setImageBitmap", icPlus);
        rv.setBitmap(R.id.notification_btnDec, "setImageBitmap", icMinus);
        rv.setBitmap(R.id.notification_btnReset, "setImageBitmap", icRestore);
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
