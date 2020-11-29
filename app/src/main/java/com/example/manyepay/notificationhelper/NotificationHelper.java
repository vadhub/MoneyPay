package com.example.manyepay.notificationhelper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.manyepay.R;

public class NotificationHelper extends ContextWrapper {
    private NotificationManager mNotification;

    private int randName = (int) (Math.random()*1000);

    private final String CHANNEL_ID = "channel1ID"+randName;
    private final String CHANNEL_NAME = "channel1"+randName;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

        System.out.println(CHANNEL_ID+" "+CHANNEL_NAME);
        channel.setLightColor(R.color.design_default_color_primary);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager(){
        if(mNotification == null){
            mNotification = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotification;
    }

    public NotificationCompat.Builder getNotification(String title, String message, String ticker, Intent intent){
        PendingIntent pading = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ruble)
                .setContentIntent(pading)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ruble))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(message)
                .setTicker(ticker)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
}
