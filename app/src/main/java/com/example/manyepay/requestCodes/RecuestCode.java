package com.example.manyepay.requestCodes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "requetsCodes")
public class RecuestCode {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int requestCode;

    public RecuestCode(int id, int requestCode) {
        this.id = id;
        this.requestCode = requestCode;
    }

    @Ignore
    public RecuestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
