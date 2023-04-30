package com.example.notepad.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    @Insert
    fun insertAll(notes: List<Note>)

    @Delete
    fun delete(notes: List<Note>)

    @Update
    fun update(notes: Note)

    @Query("SELECT * FROM note_table")
    fun getAll(): Flow<List<Note>>

    @Query("DELETE FROM note_table")
    fun dropDatabase()
}