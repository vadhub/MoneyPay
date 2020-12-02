package com.example.manyepay.notificationhelper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.manyepay.MainActivity;
import com.example.manyepay.editnotefragment.EditNoteFragment;
import com.example.manyepay.notification.Notification;
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
        notificationHelper.createNotification(System.currentTimeMillis());
    }
}
