package com.example.manyepay.reqestcodes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RequestDao {
    @Query("SELECT * FROM requetsCodes order by id")
    LiveData<List<RequestCode>> getAllCodes();

    @Delete
    void deleteCode(RequestCode code);

    @Insert
    void insertCode(RequestCode code);

    @Query("DELETE FROM requetsCodes")
    void deleteAllCodes();
}
