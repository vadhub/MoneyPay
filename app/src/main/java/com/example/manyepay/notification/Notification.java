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

    public Notification(int id, String massage, String title, String ticket) {
        this.id = id;
        this.massage = massage;
        this.title = title;
        this.ticket = ticket;
    }

    @Ignore
    public Notification(String massage, String title, String ticket) {
        this.massage = massage;
        this.title = title;
        this.ticket = ticket;
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
}
