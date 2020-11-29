package com.example.manyepay.notificationhelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.manyepay.MainActivity;

public class AlarmNotifyReciever extends BroadcastReceiver {
    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "Now pay";

    @Override
    public void onReceive(Context context, Intent intent) {

//        PendingIntent panding = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setContentIntent(panding)
//                .setSmallIcon(R.drawable.ruble)
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ruble))
//                .setWhen(System.currentTimeMillis())
//                .setContentTitle("Notify!")
//                .setContentText("it's time to pay in the MoneyPay")
//                .setTicker("it's time to pay")
//                .setAutoCancel(true)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
//
//        managerCompat.notify(NOTIFY_ID, builder.build());

        NotificationHelper notificationHelper = new NotificationHelper(context);
        Intent intent1 = new Intent(context, MainActivity.class);
        NotificationCompat.Builder notificationCompat = notificationHelper.getNotification("Notify!","it's time to pay", "it's time to pay", intent1);
        notificationHelper.getManager().notify((int) (Math.random()*1000), notificationCompat.build());
    }
}
