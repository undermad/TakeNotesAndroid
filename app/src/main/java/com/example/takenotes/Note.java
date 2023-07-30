package com.example.takenotes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String tittle;

    public String description;

    public Note(String tittle, String description) {
        this.tittle = tittle;
        this.description = description;
    }
}
