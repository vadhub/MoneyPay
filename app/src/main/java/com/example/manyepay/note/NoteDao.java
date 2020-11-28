package com.example.manyepay.note;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY date")
    LiveData<List<Note>> getAllNotes();

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM notes")
    void deleteAllNotes();
}
