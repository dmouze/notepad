package com.example.notepad.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val noteName: String,
    val noteBody: String
    )
