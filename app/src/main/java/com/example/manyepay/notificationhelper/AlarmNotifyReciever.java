package com.example.manyepay.notificationhelper;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.manyepay.MainActivity;
import com.example.manyepay.R;
import com.example.manyepay.utils.UtilAlarmSet;

import java.util.concurrent.TimeUnit;


public class AlarmNotifyReciever extends BroadcastReceiver {

    NotificationManagerCompat managerCompat;
    private UtilAlarmSet alarmSet;

    @Override
    public void onReceive(Context context, Intent intent) {
        alarmSet=new UtilAlarmSet();
        managerCompat = NotificationManagerCompat.from(context);
        Intent alarm = new Intent(context, AlarmNotifyReciever.class);
        alarm.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        int requestCode = intent.getIntExtra("requestCode", 0);
        long timeRepeat = intent.getLongExtra("timeRepeat", 5000);

        System.out.println(requestCode+"---------"+timeRepeat);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, alarm, 0);
        Intent intent1 = new Intent(context, MainActivity.class);

        sentMessageOnChannel(context, intent1);

        alarmSet.setRepeatAlarmMenedger(context, pendingIntent, timeRepeat);

    }

    private void sentMessageOnChannel(Context context, Intent intent){

        PendingIntent panding = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                .setContentIntent(panding)
                .setSmallIcon(R.drawable.ruble)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ruble))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(context.getString(R.string.title))
                .setContentText(context.getString(R.string.text))
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        System.out.println("OKKK");

        managerCompat.notify(19, notification.build());

    }
}
