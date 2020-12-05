package com.example.manyepay.requestCodes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.manyepay.note.Note;

import java.util.List;

@Dao
public interface RequestDao {

    @Query("SELECT * FROM requetsCodes order by id")
    LiveData<List<Note>> getAllCodes();

    @Delete
    void deleteCode(RecuestCode code);

    @Insert
    void insertCode(RecuestCode code);

    @Query("DELETE FROM requetsCodes")
    void deleteAllCodes();
}
