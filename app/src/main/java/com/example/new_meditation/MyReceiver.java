package com.example.new_meditation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {
    public static int MID = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "com.example.meditationsleepmusic.sound";
    static String notifyMsg = "";
    Notification.Builder mNotifyBuilder;

    @RequiresApi(api = 16)
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        Intent intent2 = new Intent(context, MainActivity.class);
        intent2.setFlags(67108864);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_MUTABLE);
        Uri defaultUri = RingtoneManager.getDefaultUri(2);
        Calendar.getInstance().get(11);
        notifyMsg = "It's time for Meditation!";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap decodeFile = BitmapFactory.decodeFile(context.getResources().getResourceName(R.drawable.sleep_music), options);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "MorningNotification", 2);
            notificationChannel.setLightColor(-16776961);
            notificationChannel.setLockscreenVisibility(1);
            notificationManager.createNotificationChannel(notificationChannel);
            this.mNotifyBuilder = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID).setSmallIcon(R.drawable.sleep_music).setContentTitle("Sleep Music - Meditation Music").setContentText(notifyMsg).setStyle(new Notification.BigTextStyle().bigText(notifyMsg).setSummaryText("View more details")).setAutoCancel(true).setContentIntent(activity).setLargeIcon(decodeFile);
        } else {
            this.mNotifyBuilder = new Notification.Builder(context).setSmallIcon(R.drawable.sleep_music).setContentTitle("Sleep Music - Meditation Music").setContentText(notifyMsg).setStyle(new Notification.BigTextStyle().bigText(notifyMsg).setSummaryText("View more details")).setSound(defaultUri).setAutoCancel(true).setContentIntent(activity).setLargeIcon(decodeFile);
        }
        if (MID < 9) {
            MID++;
        } else {
            MID = 0;
        }
        notificationManager.notify(MID, this.mNotifyBuilder.build());
    }
}
