package com.example.manyepay.notificationhelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Notification;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.manyepay.MainActivity;
import com.example.manyepay.R;
import com.example.manyepay.editnotefragment.EditNoteFragment;
import com.example.manyepay.viewmodel.MainViewModel;

import java.util.List;

public class NotificationHelper{
    private NotificationManager mNotification;
    private MainViewModel viewModel;
    private Notification.Builder builder;
    private Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void createNotification(long date){
        viewModel = ViewModelProviders.of((Fragment) new Object()).get(MainViewModel.class);

        LiveData<List<com.example.manyepay.notification.Notification>> notificationsFromDB = viewModel.getNotifications();

        notificationsFromDB.observe((LifecycleOwner) context.getApplicationContext(), new Observer<List<com.example.manyepay.notification.Notification>>() {
            @Override
            public void onChanged(List<com.example.manyepay.notification.Notification> notifications) {
                for (com.example.manyepay.notification.Notification notification: notifications) {
                    if(notification.getDate()==date){
                        setManagerNotification(notification.getTicket(), notification.getTitle(), notification.getMassage(), notification.getId());
                    }
                }

            }
        });

    }


    public void setManagerNotification(String ticker, String title, String massage, int id){
        builder = new Notification.Builder(context);
        mNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent panding = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder
                .setContentIntent(panding)
                .setSmallIcon(R.drawable.ruble)
                .setContentTitle(title)
                .setContentText(massage)
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ruble))
                .setPriority(Notification.PRIORITY_DEFAULT);

        Notification notification = builder.build();

        mNotification.notify(id, notification);
    }



//    private int randName = (int) (Math.random()*1000);
//
//    private final String CHANNEL_ID = "channel1ID"+randName;
//    private final String CHANNEL_NAME = "channel1"+randName;
//
//
//
//    public NotificationHelper(Context base) {
//        super(base);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            createChannel();
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void createChannel() {
//        viewModel = ViewModelProviders.of((FragmentActivity) this.getApplicationContext()).get(MainViewModel.class);
//        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
//
//        System.out.println(CHANNEL_ID+" "+CHANNEL_NAME);
//        channel.setLightColor(R.color.design_default_color_primary);
//
//        getManager().createNotificationChannel(channel);
//    }
//
//    public NotificationManager getManager(){
//        if(mNotification == null){
//            mNotification = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        }
//        return mNotification;
//    }
//
//    public NotificationCompat.Builder getNotification(Intent intent){
//
//        LiveData<List<Notification>> notificationFromDb = viewModel.getNotifications();
//        NotificationCompat.Builder notificationBuild;
//
//        notificationFromDb.observe((LifecycleOwner) this.getApplicationContext(), new Observer<List<Notification>>() {
//
//            @Override
//            public void onChanged(List<Notification> notifications) {
//                for (Notification notification: notifications) {
//                    if(notification.getDate()==System.currentTimeMillis()){
//
//                        notificationBuild = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
//                                .setSmallIcon(R.drawable.ruble)
//                                .setContentIntent(pading)
//                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ruble))
//                                .setWhen(System.currentTimeMillis())
//                                .setContentTitle(notification.getTitle())
//                                .setContentText(notification.getMassage())
//                                .setTicker(notification.getTicket())
//                                .setAutoCancel(true)
//                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                    }
//                }
//
//            }
//        });
//        return notificationBuild;
//    }
}
