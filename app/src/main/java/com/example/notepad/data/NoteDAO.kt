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
    suspend fun insertAll(notes: List<Note>)

    @Delete
    suspend fun delete(notes: List<Note>)

    @Update
    suspend fun update(notes: Note)

    @Query("SELECT * FROM note_table")
    fun getAll(): Flow<List<Note>>

    @Query("DELETE FROM note_table")
    suspend fun dropDatabase()
}