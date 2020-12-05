package com.example.manyepay.requestCodes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "requetsCodes")
public class RecuestCode {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private long requestCode;

    public RecuestCode(int id, long requestCode) {
        this.id = id;
        this.requestCode = requestCode;
    }

    @Ignore
    public RecuestCode(long requestCode) {
        this.requestCode = requestCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(long requestCode) {
        this.requestCode = requestCode;
    }
}
