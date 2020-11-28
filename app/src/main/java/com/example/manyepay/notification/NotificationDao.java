package com.example.manyepay.notification;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY id")
    LiveData<List<Notification>> getAllNotification();

    @Insert
    void insert(Notification notification);

    @Delete
    void delete(Notification notification);

    @Query("DELETE FROM notifications")
    void deleteAllNotifications();
}
