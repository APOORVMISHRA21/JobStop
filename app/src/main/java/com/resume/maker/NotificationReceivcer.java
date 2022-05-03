package com.resume.maker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;

import com.example.demo.R;



public class NotificationReceivcer extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    void showNotification(Context context) {
        NotificationCompat.Builder builder;
        String string = context.getResources().getString(R.string.app_name);
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtras(new Bundle());
        intent.setFlags(201326592);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel("your_name", string, NotificationManager.IMPORTANCE_HIGH));
            builder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.logo).setLargeIcon(decodeResource).setLights(SupportMenu.CATEGORY_MASK, 300, 300).setChannelId("your_name").setContentTitle("Hello");
        } else {
            builder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.logo).setPriority(1).setContentTitle("Hello");
        }
        builder.setContentIntent(activity);
        builder.setContentText("We miss you");
        builder.setAutoCancel(true);
        notificationManager.notify(1, builder.build());
    }
}
