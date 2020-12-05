package com.example.manyepay.reqestcodes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RequestDao {
    @Query("SELECT * FROM requestCodes ORDER BY id")
    LiveData<List<RequestCode>> getAllCodes();

    @Delete
    void deleteCode(RequestCode code);

    @Insert
    void insertCode(RequestCode code);

    @Query("DELETE FROM requestCodes")
    void deleteAllCodes();
}
