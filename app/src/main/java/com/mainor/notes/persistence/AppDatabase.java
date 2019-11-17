package com.mainor.notes.persistence;

import android.content.Context;
import android.provider.SyncStateContract;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;

import com.mainor.notes.entities.Note;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Database(entities = {Note.class}, version = 2, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract NoteDao noteDao();
    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, AppDatabase.class.getName())
                    //if you want create db only in memory, not in file
                    //Room.inMemoryDatabaseBuilder
                    //(context.getApplicationContext(), DataRoomDbase.class)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public class TimestampConverter {
         String pattern = "EEEEE dd MMMMM yyyy HH:mm:ss.SSSZ";
         DateFormat df =new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

        @TypeConverter
        public  Date fromTimestamp(String value) {
            if (value != null) {
                try {
                    return df.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            } else {
                return null;
            }
        }
    }
}
