package com.example.manyepay.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.manyepay.databasenote.NotesDatabase;
import com.example.manyepay.note.Note;
import com.example.manyepay.notification.Notification;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static NotesDatabase databaseNotes;
    private LiveData<List<Note>> notes;

    private LiveData<List<Notification>> notifications;
    public MainViewModel(@NonNull Application application) {
        super(application);
        databaseNotes = NotesDatabase.getInstance(getApplication());
        notes = databaseNotes.notesDao().getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void insertNote(Note note){
        new InsertTask().execute(note);
    }

    public void DeleteNote(Note note){
        new DeleteTask().execute(note);
    }

    public void DeleteAllNotes(){
        new DeleteAllTask().execute();
    }


    private static class InsertTask extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if(notes!=null&&notes.length>0){
                databaseNotes.notesDao().insert(notes[0]);
            }
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Note, Void, Void>{
        @Override
        protected Void doInBackground(Note... notes) {
            if(notes!=null&&notes.length>0){
                databaseNotes.notesDao().delete(notes[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            databaseNotes.notesDao().deleteAllNotes();
            return null;
        }
    }

}
