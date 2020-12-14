package com.example.manyepay.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.manyepay.notificationhelper.AlarmNotifyReciever;

public class UtilAlarmSet {

    private static final long MILESSEC_MIN = 60*1000;
    private static final long MILESSEC_HOUR = 60*60*1000;
    private static final long MILESSEC_WEEK = 604800000;
    private static final long MILESSEC_MONTH = 2628002880L;
    private static final long MILESSEC_YEAR = 31536000000L;

    public static long getMilessecMin() {
        return MILESSEC_MIN;
    }

    public static long getMilessecHour() {
        return MILESSEC_HOUR;
    }

    public static long getMilessecWeek() {
        return MILESSEC_WEEK;
    }

    public static long getMilessecMonth() {
        return MILESSEC_MONTH;
    }

    public static long getMilessecYear() {
        return MILESSEC_YEAR;
    }

    public void cancelAlarm(Context context, int request){
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmNotifyReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request, intent, 0);
        manager.cancel(pendingIntent);
    }

    public void setAlarmMenedger(Context context, PendingIntent pendingIntent, long timeRepeatR, long timeWakeUp){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        assert alarmManager != null;

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeWakeUp, pendingIntent);

    }

    public void setRepeatAlarmMenedger(Context context, PendingIntent pendingIntent, long timeRepeatR){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        assert alarmManager != null;

        //alarmManager.set(AlarmManager.RTC_WAKEUP, timeWakeUp, pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timeRepeatR, pendingIntent);
    }
}
