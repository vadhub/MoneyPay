package com.example.manyepay.notificationhelper;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.manyepay.MainActivity;
import com.example.manyepay.R;


public class AlarmNotifyReciever extends BroadcastReceiver {

    NotificationManagerCompat managerCompat;

    @Override
    public void onReceive(Context context, Intent intent) {
        managerCompat = NotificationManagerCompat.from(context);
        Intent intent1 = new Intent(context, MainActivity.class);

        String message = intent.getExtras().getString("message");

        sentMessageOnChannel(context, intent1, message);
    }

    private void sentMessageOnChannel(Context context, Intent intent, String message){

        PendingIntent panding = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                .setContentIntent(panding)
                .setSmallIcon(R.drawable.ruble)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ruble))
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Notify!")
                .setContentText("it's time to pay "+message)
                .setTicker("it's time to pay")
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        managerCompat.notify(19, notification.build());

    }
}
