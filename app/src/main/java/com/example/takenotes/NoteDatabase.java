package com.example.takenotes;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    // Room database will create body for that method
    public abstract NoteDao noteDao();



    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NoteDatabase.class,
                            "note_database"
                    )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            NoteDao noteDao = instance.noteDao();
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                noteDao.insert(new Note("Title 1", "Description 1"));
                noteDao.insert(new Note("Title 2", "Description 2"));
                noteDao.insert(new Note("Title 3", "Description 3"));
                noteDao.insert(new Note("Title 4", "Description 4"));
                noteDao.insert(new Note("Title 5", "Description 5"));
                noteDao.insert(new Note("Title 6", "Description 6"));
                noteDao.insert(new Note("Title 7", "Description 7"));
            });

//            new PopulateDbAsyncTask(instance).execute();
        }
    };






//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        private NoteDao noteDao;
//
//        private PopulateDbAsyncTask(NoteDatabase database) {
//
//            noteDao = database.noteDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            noteDao.insert(new Note("Title 1", "Description 1"));
//            noteDao.insert(new Note("Title 2", "Description 2"));
//            noteDao.insert(new Note("Title 3", "Description 3"));
//            noteDao.insert(new Note("Title 4", "Description 4"));
//            noteDao.insert(new Note("Title 5", "Description 5"));
//            noteDao.insert(new Note("Title 6", "Description 6"));
//            noteDao.insert(new Note("Title 7", "Description 7"));
//
//            return null;
//        }
//    }
}
