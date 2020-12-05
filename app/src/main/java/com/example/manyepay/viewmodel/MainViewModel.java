package com.example.manyepay.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.manyepay.databasenote.NotesDatabase;
import com.example.manyepay.note.Note;
import com.example.manyepay.requestCodes.RecuestCode;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static NotesDatabase databaseNotes;
    private LiveData<List<Note>> notes;
    private LiveData<List<RecuestCode>> codes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        databaseNotes = NotesDatabase.getInstance(getApplication());
        notes = databaseNotes.notesDao().getAllNotes();
        codes = databaseNotes.codeDao().getAllCodes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }
    public LiveData<List<RecuestCode>> getCodes(){return codes;}


    public void insertNote(Note note){
        new InsertTask().execute(note);
    }

    public void DeleteNote(Note note){
        new DeleteTask().execute(note);
    }

    public void DeleteAllNotes(){
        new DeleteAllTask().execute();
    }

    public void insertCode(RecuestCode code){
        new InsertCodeTask().execute(code);
    }

    public void DeleteCode(RecuestCode code){
        new DeleteCodeTask().execute(code);
    }

    public void DeleteAllCodes(){
        new DeleteAllCodeTask().execute();
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

    private static class InsertCodeTask extends AsyncTask<RecuestCode, Void, Void> {
        @Override
        protected Void doInBackground(RecuestCode... recuestCodes) {
            if(recuestCodes!=null&&recuestCodes.length>0){
                databaseNotes.codeDao().insertCode(recuestCodes[0]);
            }
            return null;
        }
    }

    private static class DeleteCodeTask extends AsyncTask<RecuestCode, Void, Void>{
        @Override
        protected Void doInBackground(RecuestCode... recuestCodes) {
            if(recuestCodes!=null&&recuestCodes.length>0){
                databaseNotes.codeDao().deleteCode(recuestCodes[0]);
            }
            return null;
        }
    }

    private static class DeleteAllCodeTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            databaseNotes.codeDao().deleteAllCodes();
            return null;
        }
    }

}
