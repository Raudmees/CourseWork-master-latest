package com.mainor.notes.entities;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import org.joda.time.DateTime;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    String title;


    @ColumnInfo(name = "content")
    String content;


    long creationDate;


    long updateDate;



    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }
}
