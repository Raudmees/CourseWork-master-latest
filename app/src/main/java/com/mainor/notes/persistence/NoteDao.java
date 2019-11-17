package com.mainor.notes.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mainor.notes.entities.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    public List<Note> selectAllNotes();

    @Query("Select * FROM note WHERE id=(:noteId)")
    public Note selectNote(int noteId);

    @Update
    public void updateNote(Note note);

    @Insert
    public long insert(Note note);

    @Delete
    public void deleteNote(Note note);
}
