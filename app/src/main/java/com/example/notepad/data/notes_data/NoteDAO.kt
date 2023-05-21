package com.example.notepad.data.notes_data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface NoteDAO {

    @Query("SELECT * FROM Notes WHERE notes.userId=:userId")
    suspend fun getNotesByUserId(userId: Int): List<Note>

    @Query("SELECT * FROM Notes WHERE notes.id=:id")
    suspend fun getNoteById(id: Int): Note?

    @Query("SELECT * FROM Notes ORDER BY dateUpdated DESC")
    suspend fun getNotes(): List<Note>

    @Delete
    suspend fun deleteNote(note: Note) : Int

    @Update
    suspend fun updateNote(note: Note) : Int

    @Insert
    suspend fun insertNote(note: Note)

}