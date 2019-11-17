package com.mainor.notes.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mainor.notes.entities.Note;

import java.util.List;

public class NotePersistence {
    private static volatile NotePersistence.AppDatabase db;

    public static synchronized NotePersistence.AppDatabase getDb(Context context){
        if(db == null){
            db = Room.databaseBuilder(context.getApplicationContext(),
                    NotePersistence.AppDatabase.class, "database-name").build();
        }
        return db;
    }

    @Dao
    public interface NoteDao {
        @Query("SELECT * FROM note")
        List<Note>  getAll();

        @Query("SELECT * FROM note WHERE id IN (:userIds)")
        List<Note>  loadAllByIds(int[] userIds);

        @Query("SELECT * FROM note WHERE id=(:id)")
        Note loadById(int id);

        @Query("SELECT * FROM note")
        List<Note> loadAll();

        @Insert
        void insertAll(Note... notes);

        @Insert
        void insert(Note note);

        @Delete
        void delete(Note note);
    }

    @Database(entities = {Note.class}, version = 2, exportSchema = false)
    public static abstract class AppDatabase extends RoomDatabase {
        public abstract NoteDao noteDao();
    }
}
