package com.example.manyepay.reqestcodes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "requetsCodes")
public class RequestCode {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int requestCodeIndef;
    private String key;

    public RequestCode(int id, int requestCodeIndef, String key) {
        this.id = id;
        this.requestCodeIndef = requestCodeIndef;
        this.key  = key;
    }

    @Ignore
    public RequestCode(int requestCodeIndef, String key) {
        this.requestCodeIndef = requestCodeIndef;
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestIndefCode() {
        return requestCodeIndef;
    }

    public void setRequestIndefCode(int requestCodeIndef) {
        this.requestCodeIndef = requestCodeIndef;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
