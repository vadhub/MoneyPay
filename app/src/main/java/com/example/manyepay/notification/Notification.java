package com.example.manyepay.notification;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class Notification {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String massage;
    private String title;
    private String ticket;
    private long date;

    public Notification(int id, String massage, String title, String ticket, long date) {
        this.id = id;
        this.massage = massage;
        this.title = title;
        this.ticket = ticket;
        this.date = date;
    }

    @Ignore
    public Notification(String massage, String title, String ticket, long date) {
        this.massage = massage;
        this.title = title;
        this.ticket = ticket;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
