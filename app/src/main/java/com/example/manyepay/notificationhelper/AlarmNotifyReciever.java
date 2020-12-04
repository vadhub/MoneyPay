package com.example.manyepay.notificationhelper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.manyepay.viewmodel.MainViewModel;

import java.util.List;

public class AlarmNotifyReciever extends BroadcastReceiver {
    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "Now pay";

    private MainViewModel viewModel;

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
    }
}
