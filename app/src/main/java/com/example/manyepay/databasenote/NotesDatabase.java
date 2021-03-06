package com.example.manyepay.databasenote;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.manyepay.note.Note;
import com.example.manyepay.note.NoteDao;
import com.example.manyepay.reqestcodes.RequestCode;
import com.example.manyepay.reqestcodes.RequestDao;

@Database(entities = {Note.class, RequestCode.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    private static NotesDatabase databaseNotes;
    private static final String DB_NAME = "notes7.db";

    private static final Object LOCK = new Object();

    public static NotesDatabase getInstance(Context context){
        synchronized (LOCK){

            if(databaseNotes==null){
                databaseNotes = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME).build();
            }
        }
        return databaseNotes;
    };

    public abstract NoteDao notesDao();
    public abstract RequestDao codeDao();
}
