package com.example.takenotes;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> notes;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        notes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
//        new InsertNoteAsyncTask(noteDao).execute(note);

        executorService.execute(() -> {
            noteDao.insert(note);
        });

    }

    public void update(Note note) {
//        new UpdateNoteAsyncTask(noteDao).execute(note);

        executorService.execute(() -> {
            noteDao.update(note);
        });
    }

    public void delete(Note note) {
//        new DeleteNoteAsyncTask(noteDao).execute(note);
        executorService.execute(() -> {
            noteDao.delete(note);
        });
    }

    public LiveData<List<Note>> getAllNotes() {
        return notes;
    }











        // 1. Parameter for doInBackground method
        // 2. Parameter for onProgressUpdate method
        // 3. Parameter return type of doInBackground method
//    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
//
//        private NoteDao noteDao;
//
//        private InsertNoteAsyncTask(NoteDao noteDao) {
//            this.noteDao = noteDao;
//        }
//        @Override
//        protected Void doInBackground(Note... notes) {
//
//            noteDao.insert(notes[0]);
//            return null;
//        }
//
//    }
//
//    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
//
//        private NoteDao noteDao;
//
//        private UpdateNoteAsyncTask(NoteDao noteDao) {
//            this.noteDao = noteDao;
//        }
//        @Override
//        protected Void doInBackground(Note... notes) {
//
//            noteDao.update(notes[0]);
//            return null;
//        }
//
//    }
//
//    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
//
//        private NoteDao noteDao;
//
//        private DeleteNoteAsyncTask(NoteDao noteDao) {
//            this.noteDao = noteDao;
//        }
//        @Override
//        protected Void doInBackground(Note... notes) {
//
//            noteDao.delete(notes[0]);
//            return null;
//        }
//
//    }


}
